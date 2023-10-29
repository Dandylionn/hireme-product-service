package com.hireme.assignment.controller;
import com.hireme.product.assignment.enums.AssignmentStatus;
import com.hireme.product.assignment.enums.AssignmentType;
import com.hireme.product.assignment.enums.Subject;
import com.hireme.product.assignment.enums.SubjectLevel;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hireme.product.ProductApplication;
import com.hireme.product.assignment.controller.AssignmentController;
import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.service.AssignmentService;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;

import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.MediaType;

import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import org.springframework.test.util.ReflectionTestUtils;
import static org.mockito.Mockito.when;


import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.hireme.assignment")
@SpringBootTest(classes = ProductApplication.class)
public class AssignmentControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private AssignmentService assignmentService;
    @InjectMocks
    private AssignmentController assignmentController;
    @Autowired
    private ObjectMapper objectMapper;

    public AssignmentControllerTest() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateAssignment() throws Exception {
        // Prepare a sample AssignmentDTO
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("Math Tuition");
        assignmentDTO.setDescription("Dummy Description");
        assignmentDTO.setByUser("Dummy Name");
        assignmentDTO.setStatus(AssignmentStatus.IN_PROGRESS); // can choose any status from enum
        assignmentDTO.setTuitionDuration("60"); // Set the duration in minutes

        // Set createdDateTime
//        assignmentDTO.setCreatedDateTime(LocalDateTime.now());
//        assignmentDTO.formatCreatedDateTime(); // Format the date and time

        assignmentDTO.setAssignmentType(AssignmentType.LOOKING_FOR_TUITION);
        assignmentDTO.setSubjectLevel(SubjectLevel.PRIMARY);
        assignmentDTO.setSubject(Subject.PRIMARY_MATHEMATICS);
        assignmentDTO.setLocation("Dummy Location");
        assignmentDTO.setCreatedByUserId("5545");
//        assignmentDTO.setTuitionFrequencies(new HashSet<>());
        assignmentDTO.setPrice(new BigDecimal("50.0"));
        LocalDateTime createdDateTime = LocalDateTime.now();
        assignmentDTO.setCreatedDateTime(createdDateTime);
        LocalDateTime updatedDateTime = LocalDateTime.now();
        assignmentDTO.setUpdatedDateTime(updatedDateTime);
        LocalDateTime expirationDate = LocalDateTime.now();
        assignmentDTO.setExpirationDate(expirationDate);

//        assignmentDTO.setCreatedDateTime(LocalDateTime.now());
//        assignmentDTO.setUpdatedDateTime(LocalDateTime.now());
//        assignmentDTO.setExpirationDate(LocalDateTime.now());

        // Mock the behavior of assignmentService.createAssignment
//        Mockito.when(assignmentService.createAssignment(ArgumentMatchers.any(AssignmentDTO.class))).thenReturn(assignmentDTO);
        Mockito.when(assignmentService.createAssignment(ArgumentMatchers.any(AssignmentDTO.class)))
                .thenAnswer(invocation -> {
                    AssignmentDTO argument = invocation.getArgument(0);
                    // Create and return a mock AssignmentDTO based on the provided argument.
                    return createSampleAssignmentDTO();
                });
        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/assignments")
                        .header("USER-ID", "5545")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Math Tuition"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Dummy Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byUser").value("Dummy Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.tuitionDuration").value(60))
                .andExpect(MockMvcResultMatchers.jsonPath("$.assignmentType").value("LOOKING_FOR_TUITION"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subjectLevel").value("PRIMARY"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.subject").value("PRIMARY_MATHEMATICS"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.location").value("Dummy Location"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdByUserId").value("5545"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.price").value("50.0"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDateTime").value(assignmentDTO.formatCreatedDateTime()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.updatedDateTime").value(assignmentDTO.formatUpdatedDateTime()))
                .andExpect(MockMvcResultMatchers.jsonPath("$.expirationDate").value(assignmentDTO.formatExpirationDate()));

        // Verify that assignmentService.createAssignment was called with the provided AssignmentDTO
        Mockito.verify(assignmentService, Mockito.times(1)).createAssignment(ArgumentMatchers.any(AssignmentDTO.class));
    }

    @Test
    public void testCreateAssignmentAuthorized() throws Exception {
        // Mock the assignmentService.createAssignment method to return a created assignment
        AssignmentDTO createdAssignment = createSampleAssignmentDTO();
        Mockito.when(assignmentService.createAssignment(ArgumentMatchers.any(AssignmentDTO.class)))
                .thenReturn(createdAssignment);

        // Perform the POST request with a valid USER-ID header
        mockMvc.perform(MockMvcRequestBuilders.post("/assignments")
                        .header("USER-ID", "validUserId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createSampleAssignmentDTO())))
                .andExpect(status().isCreated());
    }

    private AssignmentDTO createSampleAssignmentDTO() {
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setTitle("Math Tuition");
        assignmentDTO.setDescription("Dummy Description");
        assignmentDTO.setByUser("Dummy Name");
        assignmentDTO.setStatus(AssignmentStatus.IN_PROGRESS);
        assignmentDTO.setTuitionDuration("60");
        assignmentDTO.setAssignmentType(AssignmentType.LOOKING_FOR_TUITION);
        assignmentDTO.setSubjectLevel(SubjectLevel.PRIMARY);
        assignmentDTO.setSubject(Subject.PRIMARY_MATHEMATICS);
        assignmentDTO.setLocation("Dummy Location");
        assignmentDTO.setCreatedByUserId("5545");
        assignmentDTO.setPrice(new BigDecimal("50.0"));
        assignmentDTO.setCreatedDateTime(LocalDateTime.now());
        assignmentDTO.setUpdatedDateTime(LocalDateTime.now());
        assignmentDTO.setExpirationDate(LocalDateTime.now());
        return assignmentDTO;
    }



//    @Test
//    public void testCreateAssignmentWithInvalidUser() throws Exception {
//        // Perform the POST request with an invalid USER-ID header
//        mockMvc.perform(MockMvcRequestBuilders.post("/assignments")
//                        .header("USER-ID", "invalidUserId")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(createSampleAssignmentDTO())))
//                .andExpect(status().isUnauthorized());
//    }
//    @Test
//    public void testUpdateAssignment() throws Exception {
//        // Prepare a sample AssignmentDTO to update
//        AssignmentDTO assignmentDTO = createSampleAssignmentDTO();
//        assignmentDTO.setTitle("Updated Math Tuition"); // Change the title
//
//        // Mock the behavior of assignmentService.updateAssignment
//        BDDMockito.when(assignmentService.updateAssignment(ArgumentMatchers.anyLong(), ArgumentMatchers.any(AssignmentDTO.class)))
//                .thenAnswer(invocation -> {
//                    Long assignmentId = invocation.getArgument(0); // Correctly obtain assignmentId as Long
//                    AssignmentDTO updatedAssignment = invocation.getArgument(1);
//                    // Create and return a mock AssignmentDTO with updated data.
//                    updatedAssignment.setAssignmentId(assignmentId);
//                    return updatedAssignment;
//                });
//
//        // Perform the PUT request to update the assignment
//        mockMvc.perform(MockMvcRequestBuilders
//                        .put("/assignments/{assignmentId}", 1L)
//                        .header("USER-ID", "5545")
//                        .contentType(MediaType.APPLICATION_JSON)
//                        .content(objectMapper.writeValueAsString(assignmentDTO)))
//                .andExpect(status().isOk())
//                .andExpect(MockMvcResultMatchers.content().json(objectMapper.writeValueAsString(assignmentDTO)));
//        // Verify that assignmentService.updateAssignment was called with the provided AssignmentDTO
//        BDDMockito.verify(assignmentService, BDDMockito.times(1))
//                .updateAssignment(ArgumentMatchers.eq(1L), ArgumentMatchers.any(AssignmentDTO.class)); // Match with Long type
//    }
//
//    @Test
//    public void testDeleteAssignment() throws Exception {
//        // Mock the behavior of assignmentService.deleteAssignment
//        BDDMockito.when(assignmentService.deleteAssignment(ArgumentMatchers.anyLong()))
//                .thenAnswer(invocation -> {
//                    Long assignmentId = invocation.getArgument(0);
//                    // Simulate a case where the assignment is not found
//                    return null;
//                });
//
//        // Perform the DELETE request to delete an assignment
//        mockMvc.perform(MockMvcRequestBuilders
//                        .delete("/assignments/{assignmentId}", 1L)
//                        .header("USER-ID", "5545"))
//                .andExpect(status().isNotFound());
//        // Verify that assignmentService.deleteAssignment was called with the provided assignment ID
//        BDDMockito.verify(assignmentService, BDDMockito.times(1))
//                .deleteAssignment(ArgumentMatchers.eq(1L)); // Match with Long type
//    }
}