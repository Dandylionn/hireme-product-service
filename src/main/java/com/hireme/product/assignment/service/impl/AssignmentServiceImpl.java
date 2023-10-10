package com.hireme.product.assignment.service.impl;
import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.entity.Assignment;
import com.hireme.product.assignment.enums.AssignmentStatus;
import com.hireme.product.exception.InvalidStatusTransitionException;
import com.hireme.product.assignment.mapper.AssignmentMapper;
import com.hireme.product.assignment.repository.AssignmentRepository;
import com.hireme.product.assignment.service.AssignmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper; // Inject the mapper

    @Autowired
    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper) {
        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
    }

    @Override
    public AssignmentDTO createAssignment(AssignmentDTO assignmentDTO) {
        // Implement the logic to create an assignment in the database.
        // Map AssignmentDTO to Assignment entity and save it using the repository.
        // Return the created AssignmentDTO.

        // Map AssignmentDTO to Assignment entity (w/o mapper)
//        Assignment assignment = new Assignment();
//        assignment.setTitle(assignmentDTO.getTitle());
//        assignment.setDescription(assignmentDTO.getDescription());
//        assignment.setByUser(assignmentDTO.getByUser());
//        assignment.setTuitionDuration(assignmentDTO.getTuitionDuration());
//        assignment.setStatus(assignmentDTO.getStatus());

        // Map AssignmentDTO to Assignment entity using the mapper
        Assignment assignment = assignmentMapper.assignmentDTOToAssignment(assignmentDTO);

        // Set the status to OPEN for a new assignment
        assignment.setStatus(AssignmentStatus.OPEN);
        // Save the Assignment entity using the repository
        Assignment savedAssignment = assignmentRepository.save(assignment);

        // Map the saved Assignment entity back to AssignmentDTO (w/o mapper)
//        AssignmentDTO createdAssignmentDTO = new AssignmentDTO();
//        createdAssignmentDTO.setAssignmentId(savedAssignment.getAssignmentId());
//        createdAssignmentDTO.setTitle(savedAssignment.getTitle());
//        createdAssignmentDTO.setDescription(savedAssignment.getDescription());
//        createdAssignmentDTO.setByUser(savedAssignment.getByUser());
//        createdAssignmentDTO.setTuitionDuration(savedAssignment.getTuitionDuration());
//        createdAssignmentDTO.setStatus(savedAssignment.getStatus());
        // Map the saved Assignment entity back to AssignmentDTO
        AssignmentDTO createdAssignmentDTO = assignmentMapper.assignmentToAssignmentDTO(savedAssignment);
        return createdAssignmentDTO;
    }

    @Override
    public AssignmentDTO updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO) {
        // Implement the logic to update an assignment in the database.
        // Fetch the assignment by ID, update its fields with data from assignmentDTO, and save it.
        // Return the updated AssignmentDTO.

        // Fetch the assignment by ID from the repository
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));
        // Check if the transition is allowed based on rules
        if (!isStatusTransitionAllowed(existingAssignment.getStatus(), assignmentDTO.getStatus())) {
            throw new InvalidStatusTransitionException("Invalid status transition");

        }

        // Update the fields of the existing assignment with data from assignmentDTO (w/o Mapper)
        existingAssignment.setTitle(assignmentDTO.getTitle());
        existingAssignment.setDescription(assignmentDTO.getDescription());
        existingAssignment.setLocation(assignmentDTO.getLocation());
        existingAssignment.setByUser(assignmentDTO.getByUser());
        existingAssignment.setTuitionDuration(assignmentDTO.getTuitionDuration());
        existingAssignment.setStatus(assignmentDTO.getStatus());
        existingAssignment.setCreatedDateTime(assignmentDTO.getCreatedDateTime());


//        // Set the status based on the value in assignmentDTO
//        existingAssignment.setStatus(assignmentDTO.getStatus());

        // Save the updated Assignment entity
        Assignment updatedAssignment = assignmentRepository.save(existingAssignment);

        // Map the updated Assignment entity back to AssignmentDTO (w/o Mapper)
//        AssignmentDTO updatedAssignmentDTO = new AssignmentDTO();
//        updatedAssignmentDTO.setAssignmentId(updatedAssignment.getAssignmentId());
//        updatedAssignmentDTO.setTitle(updatedAssignment.getTitle());
//        updatedAssignmentDTO.setDescription(updatedAssignment.getDescription());
//        updatedAssignmentDTO.setByUser(updatedAssignment.getByUser());
//        updatedAssignmentDTO.setTuitionDuration(updatedAssignment.getTuitionDuration());
//        updatedAssignmentDTO.setStatus(updatedAssignment.getStatus());
        // Map the updated Assignment entity back to AssignmentDTO
        AssignmentDTO updatedAssignmentDTO = AssignmentMapper.INSTANCE.assignmentToAssignmentDTO(updatedAssignment);

        return updatedAssignmentDTO;
    }
    // Define a method to check if the status transition is allowed
    private boolean isStatusTransitionAllowed(AssignmentStatus currentStatus, AssignmentStatus newStatus) {
        // Implement rules for status transitions here
        // allow transitions from OPEN to IN_PROGRESS and from IN_PROGRESS to COMPLETED.
        return (currentStatus == AssignmentStatus.OPEN && newStatus == AssignmentStatus.IN_PROGRESS) ||
                (currentStatus == AssignmentStatus.IN_PROGRESS && newStatus == AssignmentStatus.COMPLETED);
    }

    @Override
    public void deleteAssignment(Long assignmentId) {
        // Implement the logic to delete an assignment from the database by its ID.
        // Delete the assignment by ID using the repository
        assignmentRepository.deleteById(assignmentId);
    }

    @Override
    public AssignmentDTO getAssignmentById(Long assignmentId) {
        // Implement the logic to retrieve an assignment by its ID from the database.
        // Map the retrieved Assignment entity to AssignmentDTO and return it.


        // Fetch the assignment by ID from the repository
        Assignment assignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));

        // Map the retrieved Assignment entity to AssignmentDTO
        AssignmentDTO assignmentDTO = new AssignmentDTO();
        assignmentDTO.setAssignmentId(assignment.getAssignmentId());
        assignmentDTO.setTitle(assignment.getTitle());
        assignmentDTO.setDescription(assignment.getDescription());
        assignmentDTO.setLocation(assignment.getLocation());
        assignmentDTO.setByUser(assignment.getByUser());
        assignmentDTO.setTuitionDuration(assignment.getTuitionDuration());
        assignmentDTO.setStatus(assignment.getStatus());
        assignmentDTO.setCreatedDateTime(assignment.getCreatedDateTime());

        return assignmentDTO;
    }
    // Implement other assignment-related methods here if needed.
}