package com.hireme.recommendation.service.impl;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.recommendation.service.impl.RecommendationServiceImpl;
import com.hireme.product.assignment.service.AssignmentService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.when;
import static org.junit.Assert.assertEquals;

public class RecommendationServiceImplTest {

    private RecommendationServiceImpl recommendationService;

    @Mock
    private AssignmentService assignmentService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        recommendationService = new RecommendationServiceImpl(null, null, assignmentService);
    }

    @Test
    public void testGetRecommendationsByAssignmentId() {
        Long assignmentId = 1L;

        // Create a target assignment for testing
        AssignmentDTO targetAssignment = new AssignmentDTO();
        targetAssignment.setAssignmentId(assignmentId);
        targetAssignment.setTitle("Test Assignment");

        // Create a list of all assignments
        List<AssignmentDTO> allAssignments = new ArrayList<>();
        AssignmentDTO assignment1 = new AssignmentDTO();
        assignment1.setAssignmentId(2L);
        assignment1.setTitle("Assignment A, Test Assignment");
        AssignmentDTO assignment2 = new AssignmentDTO();
        assignment2.setAssignmentId(3L);
        assignment2.setTitle("Assignment B, Test Assignment");
        AssignmentDTO assignment3 = new AssignmentDTO();
        assignment3.setAssignmentId(4L);
        assignment3.setTitle("DUMMY C");

        allAssignments.add(assignment1);
        allAssignments.add(assignment2);
        allAssignments.add(assignment3);

        // Mock the behavior of assignmentService
        when(assignmentService.getAssignmentById(assignmentId)).thenReturn(targetAssignment);
        when(assignmentService.getAllAssignments()).thenReturn(allAssignments);

        List<AssignmentDTO> recommendations = recommendationService.getRecommendationsByAssignmentId(assignmentId);

        // Verify that the correct assignments were recommended
        List<AssignmentDTO> expectedRecommendations = new ArrayList<>();
        expectedRecommendations.add(assignment1);
        expectedRecommendations.add(assignment2);
        assertEquals(expectedRecommendations.size(), recommendations.size());
        for (AssignmentDTO expectedAssignment : expectedRecommendations) {
            boolean found = recommendations.stream()
                    .anyMatch(assignment -> assignment.getAssignmentId().equals(expectedAssignment.getAssignmentId()));
            assertEquals(true, found);
        }
    }
}
