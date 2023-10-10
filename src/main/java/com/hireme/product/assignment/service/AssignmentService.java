package com.hireme.product.assignment.service;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.repository.AssignmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public interface AssignmentService {
    @Autowired
//    private AssignmentRepository assignmentRepository;

    AssignmentDTO createAssignment(AssignmentDTO assignmentDTO);
    AssignmentDTO updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO);
    void deleteAssignment(Long assignmentId);
    AssignmentDTO getAssignmentById(Long assignmentId);
    // Add other assignment-related methods if necessary.

}
