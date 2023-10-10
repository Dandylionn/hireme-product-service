package com.hireme.assignment.service;

import com.hireme.assignment.dto.AssignmentDTO;
import com.hireme.assignment.entity.Assignment;
import com.hireme.assignment.enums.AssignmentStatus;
import com.hireme.assignment.repository.AssignmentRepository;
import com.hireme.assignment.service.impl.AssignmentServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AssignmentServiceTest {
    @InjectMocks
    private AssignmentServiceImpl assignmentService;

    @Mock
    private AssignmentRepository assignmentRepository;

    @Test
    public void testValidStatusTransition() {
        // Create a sample assignment with an OPEN status
        Assignment assignment = new Assignment();
        assignment.setStatus(AssignmentStatus.OPEN);

        // Create an AssignmentDTO with a new status (e.g., IN_PROGRESS)
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setStatus(AssignmentStatus.IN_PROGRESS);

        // Mock repository behavior
        when(assignmentRepository.findById(anyLong())).thenReturn(Optional.of(assignment));
        when(assignmentRepository.save(any())).thenReturn(assignment);

        // Call the updateAssignment method
        AssignmentDTO updatedAssignmentDTO = assignmentService.updateAssignment(1L, assignmentDTO);

        // Assert that the status transition was successful
        assertEquals(AssignmentStatus.IN_PROGRESS, updatedAssignmentDTO.getStatus());
    }

}
