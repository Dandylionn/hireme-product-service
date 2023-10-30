package com.hireme.recommendation.controller;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.recommendation.controller.RecommendationController;
import com.hireme.product.recommendation.service.RecommendationService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

public class RecommendationControllerTest {

    private MockMvc mockMvc;

    @InjectMocks
    private RecommendationController recommendationController;

    @Mock
    private RecommendationService recommendationService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(recommendationController).build();
    }

    @Test
    public void testGetRecommendationsByAssignmentId() throws Exception {
        Long assignmentId = 1L;

        // Create a list of recommended assignments
        List<AssignmentDTO> recommendations = new ArrayList<>();
        AssignmentDTO assignment1 = new AssignmentDTO();
        assignment1.setAssignmentId(2L);
        assignment1.setTitle("Recommended Assignment 1");
        AssignmentDTO assignment2 = new AssignmentDTO();
        assignment2.setAssignmentId(3L);
        assignment2.setTitle("Recommended Assignment 2");

        recommendations.add(assignment1);
        recommendations.add(assignment2);

        // Mock the behavior of recommendationService
        when(recommendationService.getRecommendationsByAssignmentId(assignmentId)).thenReturn(recommendations);

        // Perform a GET request to retrieve recommendations
        mockMvc.perform(get("/recommendations/assignment/{assignmentId}", assignmentId)
                        .header("USER-ID", "userId123") // Replace with a valid user ID
                        .contentType("application/json"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.recommendations[0].assignmentId").value(2))
                .andExpect(jsonPath("$.recommendations[0].title").value("Recommended Assignment 1"))
                .andExpect(jsonPath("$.recommendations[1].assignmentId").value(3))
                .andExpect(jsonPath("$.recommendations[1].title").value("Recommended Assignment 2"));
    }
}
