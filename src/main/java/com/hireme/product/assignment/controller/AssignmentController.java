package com.hireme.product.assignment.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.service.*;
//for swagger
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
//
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

//for calling other microservices
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
//
import java.io.IOException;
import java.util.List;
import java.time.LocalDateTime;


@RestController
@RequestMapping("/assignments")
public class AssignmentController {

    private final AssignmentService assignmentService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    private final RestTemplate restTemplate;
    @Autowired
    public AssignmentController(AssignmentService assignmentService, JdbcTemplate jdbcTemplate,
                                RestTemplate restTemplate) {
        this.assignmentService = assignmentService;
        this.jdbcTemplate = jdbcTemplate;
        this.restTemplate = restTemplate;
    }

    // Endpoint to create a new assignment
    @PostMapping
    //for swagger
    @Operation(summary = "Create a new assignment")
    @ApiResponse(responseCode = "201", description = "Assignment created successfully")
    //
    public ResponseEntity<AssignmentDTO> createAssignment
    (@RequestHeader("USER-ID") String userId,
     @RequestBody AssignmentDTO assignmentDTO) {
        // Check if the user is authorized to create the assignment based on the USER-ID header
//        if (!isValidUser(userId)) {
//            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Unauthorized
//        }
        // Set the createdByUserId based on the USER-ID
        assignmentDTO.setCreatedByUserId(userId);
        try {
            // Set the createdDateTime field to the current date and time
            assignmentDTO.setCreatedDateTime(LocalDateTime.now());
            // Format the createdDateTime field before creating the assignment
            assignmentDTO.formatCreatedDateTime(); // Format the date and time

            // Print the value getByUser() for debugging
            System.out.println("Tutor Name to Fetch: " + assignmentDTO.getByUser());
            // Truncate the 'byUser' field if its length exceeds 100 characters
            if (assignmentDTO.getByUser() != null && assignmentDTO.getByUser().length() > 100) {
                assignmentDTO.setByUser(assignmentDTO.getByUser().substring(0, 100));
            }

            // Set the USER-ID header in the HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.set("USER-ID", userId); // Set the USER-ID header
            HttpEntity<AssignmentDTO> request = new HttpEntity<>(assignmentDTO, headers);

            // Make an HTTP GET request to the User microservice to fetch the tutor name
            String tutorMicroserviceUrl = "http://localhost:8083/tutors/findByName/{tutorName}";
            // Create a ParameterizedTypeReference to specify the expected response type
            ParameterizedTypeReference<String> responseType = new ParameterizedTypeReference<String>() {};
            ResponseEntity<String> responseEntity = restTemplate.exchange(
                    tutorMicroserviceUrl,
                    HttpMethod.GET,
                    null,
                    responseType,
                    assignmentDTO.getByUser()
            );
            // Check the HTTP status code of the response
            if (responseEntity.getStatusCode() == HttpStatus.OK) {
                String tutorJsonResponse = responseEntity.getBody();
                System.out.println("Response from User Microservice: " + tutorJsonResponse);

                // Parse the JSON response as an array
                ObjectMapper objectMapper = new ObjectMapper();
                List<JsonNode> jsonNodes = objectMapper.readValue(tutorJsonResponse, new TypeReference<List<JsonNode>>() {});

                if (!jsonNodes.isEmpty()) {
                    JsonNode firstNode = jsonNodes.get(0);
                    // Check if the tutorName field exists
                    if (firstNode.has("tutorName")) {
                        // Extract the tutorName
                        String retrievedTutorName = firstNode.get("tutorName").asText();
                        System.out.println("Retrieved Name: " + retrievedTutorName);
                        // Set the retrieved tutor name in the assignmentDTO
                        assignmentDTO.setByUser(retrievedTutorName);

                        // Create assignment
                        AssignmentDTO createdAssignment = assignmentService.createAssignment(assignmentDTO);
                        return new ResponseEntity<>(createdAssignment, HttpStatus.CREATED);
                    }
                }
            }
            // Handle not found
            String errorMessage = "Tutor's name not found in the JSON response from the User microservice";
            AssignmentDTO errorAssignment = createErrorAssignmentDTO(errorMessage);
            return new ResponseEntity<>(errorAssignment, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (RestClientException e) {
            // Handle the exception or return error response
            String errorMessage = "Error while communicating with the User microservice: " + e.getMessage();
            AssignmentDTO errorAssignment = createErrorAssignmentDTO(errorMessage);
            return new ResponseEntity<>(createErrorAssignmentDTO(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (IOException e) {
            // Handle JSON parsing errors
            String errorMessage = "Error parsing JSON response from the User microservice: " + e.getMessage();
            AssignmentDTO errorAssignment = createErrorAssignmentDTO(errorMessage);
            return new ResponseEntity<>(createErrorAssignmentDTO(errorMessage), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Endpoint to update an existing assignment by ID
    @PutMapping("/{assignmentId}")
    //for swagger
    @Operation(summary = "Update an existing assignment by ID")
    @ApiResponse(responseCode = "200", description = "Assignment updated successfully")
    @ApiResponse(responseCode = "404", description = "Assignment not found")
    //
    public ResponseEntity<AssignmentDTO> updateAssignment(
            @RequestHeader("USER-ID") String userId,
            @PathVariable Long assignmentId,
            @RequestBody AssignmentDTO assignmentDTO
    ) {
        // Check if the user is authorized to update the assignment based on the USER-ID header
        if (!isValidUser(userId, assignmentId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }
        try {
            // Set the USER-ID header in the HTTP request
            HttpHeaders headers = new HttpHeaders();
            headers.set("USER-ID", userId); // Set the USER-ID header
            HttpEntity<AssignmentDTO> request = new HttpEntity<>(assignmentDTO, headers);

            AssignmentDTO updatedAssignment = assignmentService.updateAssignment(assignmentId, assignmentDTO);
            return new ResponseEntity<>(updatedAssignment, HttpStatus.OK);

        } catch (RestClientException e) {
            // Handle the exception or return error response
            String errorMessage = "Error while communicating with the User microservice: " + e.getMessage();
            AssignmentDTO errorAssignment = createErrorAssignmentDTO(errorMessage);
            return new ResponseEntity<>(errorAssignment, HttpStatus.INTERNAL_SERVER_ERROR);
        } catch (Exception e) {
            // Handle other exceptions
            String errorMessage = "Error updating assignment: " + e.getMessage();
            AssignmentDTO errorAssignment = createErrorAssignmentDTO(errorMessage);
            return new ResponseEntity<>(errorAssignment, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }



    // Endpoint to delete an assignment by ID
    @DeleteMapping("/{assignmentId}")
    //for swagger
    @Operation(summary = "Delete an assignment by ID")
    @ApiResponse(responseCode = "204", description = "Assignment deleted successfully")
    @ApiResponse(responseCode = "404", description = "Assignment not found")
    //
    public ResponseEntity<AssignmentService.ServiceResponse> deleteAssignment
    (@RequestHeader("USER-ID") String userId,
     @PathVariable Long assignmentId) {
        // Check if the user is authorized to delete the assignment based on the USER-ID header
        if (!isValidUser(userId, assignmentId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }
        // Fetch the assignment by ID from your service or repository
        AssignmentDTO assignment = assignmentService.getAssignmentById(assignmentId);
        if (assignment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found
        }
        // Delete the assignment
        assignmentService.deleteAssignment(assignmentId);

        // When the deletion is successful, the ServiceResponse with a success message will be returned
        AssignmentService.ServiceResponse serviceResponse = assignmentService.deleteAssignment(assignmentId);
        resetAutoIncrement(); // Reset auto-increment after deletion
//        return new ResponseEntity<>(serviceResponse, HttpStatus.NO_CONTENT);
        return new ResponseEntity<>(serviceResponse, HttpStatus.OK);
    }


    // Endpoint to retrieve an assignment by ID
    @GetMapping("/{assignmentId}")
    //for swagger
    @Operation(summary = "Get an assignment by ID")
    @ApiResponse(responseCode = "200", description = "Assignment retrieved successfully")
    @ApiResponse(responseCode = "404", description = "Assignment not found")
    //
    public ResponseEntity<AssignmentDTO> getAssignmentById
    (@RequestHeader("USER-ID") String userId,
     @PathVariable Long assignmentId) {
        // Check if the user is authorized to retrieve the assignment based on the USER-ID header
        if (!isValidUser(userId, assignmentId)) {
            return new ResponseEntity<>(HttpStatus.UNAUTHORIZED); // 401 Unauthorized
        }
        AssignmentDTO assignmentDTO = assignmentService.getAssignmentById(assignmentId);
        return new ResponseEntity<>(assignmentDTO, HttpStatus.OK);
    }

    // Helper method to reset the auto-increment value for a table
    private void resetAutoIncrement() {
        // Execute the SQL command to reset the auto-increment value
        String resetSql = "ALTER TABLE assignment AUTO_INCREMENT = 1";
        jdbcTemplate.execute(resetSql);
    }
    // Helper method to create an error AssignmentDTO
    private AssignmentDTO createErrorAssignmentDTO(String errorMessage) {
        AssignmentDTO errorAssignment = new AssignmentDTO();
        errorAssignment.setTitle("Error");
        errorAssignment.setByUser(errorMessage);
        return errorAssignment;
    }

    // Helper method to check if the user is valid
    private boolean isValidUser(String userId, Long assignmentId) {
        // validate the user
        // check if the userId corresponds to a logged-in user
        // If not, return false, user is not authorized
        // else, return true, user is valid.
//        return true;

        // Check if the user is authorized to update or delete the assignment.
        // Compare the USER-ID in the request header with the createdByUserId of the assignment with the given assignmentId.
        // For updating an assignment:
        AssignmentDTO assignmentDTO = assignmentService.getAssignmentById(assignmentId);
        if (assignmentDTO != null) {
            return assignmentDTO.getCreatedByUserId().equals(userId);
        }
        // For deleting an assignment:
        AssignmentDTO assignment = assignmentService.getAssignmentById(assignmentId);
        return assignment != null && assignment.getCreatedByUserId().equals(userId);
    }

}