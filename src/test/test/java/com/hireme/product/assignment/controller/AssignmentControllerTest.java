package com.hireme.assignment.controller;
import com.hireme.assignment.enums.AssignmentStatus;
import org.springframework.boot.test.context.SpringBootTest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.hireme.assignment.AssignmentApplication;
import com.hireme.assignment.controller.AssignmentController;
import com.hireme.assignment.dto.AssignmentDTO;
import com.hireme.assignment.service.AssignmentService;
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

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@AutoConfigureMockMvc
@ComponentScan(basePackages = "com.hireme.assignment")
@SpringBootTest(classes = AssignmentApplication.class)
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

        // Set the AssignmentStatus to a specific value
        assignmentDTO.setStatus(AssignmentStatus.IN_PROGRESS); // can choose any status from enum


        // Set tuition duration
        assignmentDTO.setTuitionDuration(60); // Set the duration in minutes

        // Set createdDateTime
//        assignmentDTO.setCreatedDateTime(LocalDateTime.now());
//        assignmentDTO.formatCreatedDateTime(); // Format the date and time

        // Mock the behavior of assignmentService.createAssignment
//        Mockito.when(assignmentService.createAssignment(ArgumentMatchers.any(AssignmentDTO.class))).thenReturn(assignmentDTO);
        Mockito.when(assignmentService.createAssignment(ArgumentMatchers.any(AssignmentDTO.class)))
                .thenAnswer(invocation -> {
                    AssignmentDTO argument = invocation.getArgument(0);
                    // Create and return a mock AssignmentDTO based on the provided argument.

                    return argument;
                });
        // Perform the POST request
        mockMvc.perform(MockMvcRequestBuilders
                        .post("/assignments")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(assignmentDTO)))
                .andExpect(status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.title").value("Math Tuition"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.description").value("Dummy Description"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.byUser").value("Dummy Name"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.status").value("IN_PROGRESS")) // Check the status
                .andExpect(MockMvcResultMatchers.jsonPath("$.tuitionDuration").value(60)) // Check the tuition duration
                .andExpect(MockMvcResultMatchers.jsonPath("$.createdDateTime").isString()); // Check the createdDateTime





        // Verify that assignmentService.createAssignment was called with the provided AssignmentDTO
        Mockito.verify(assignmentService, Mockito.times(1)).createAssignment(ArgumentMatchers.any(AssignmentDTO.class));
    }
}