package com.hireme.product.assignment.service.impl;
import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.entity.Assignment;
import com.hireme.product.assignment.enums.*;
import com.hireme.product.exception.InvalidStatusTransitionException;
import com.hireme.product.assignment.mapper.AssignmentMapper;
import com.hireme.product.assignment.repository.AssignmentRepository;
import com.hireme.product.assignment.service.AssignmentService;
import com.hireme.product.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;


@Service
public class AssignmentServiceImpl implements AssignmentService {

    private final AssignmentRepository assignmentRepository;
    private final AssignmentMapper assignmentMapper; // Inject the mapper
    private final RecommendationService recommendationService; // Inject the RecommendationService


    @Autowired
    public AssignmentServiceImpl(AssignmentRepository assignmentRepository, AssignmentMapper assignmentMapper,
                                 @Lazy RecommendationService recommendationService) {
        //break circular dependency with Recommendation Service (Assignment Detail Page)

        this.assignmentRepository = assignmentRepository;
        this.assignmentMapper = assignmentMapper;
        this.recommendationService = recommendationService;
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
        // Fetch the assignment by ID, update its fields with data from assignmentDTO, and save it.
        // Fetch the assignment by ID from the repository
        Assignment existingAssignment = assignmentRepository.findById(assignmentId)
                .orElseThrow(() -> new ResourceNotFoundException("Assignment not found with id: " + assignmentId));
        // Check if the transition is allowed based on rules
        if (!isStatusTransitionAllowed(existingAssignment.getStatus(), assignmentDTO.getStatus())) {
            throw new InvalidStatusTransitionException("Invalid status transition");
        }


        // Update the fields of the existing assignment with data from assignmentDTO (w/o Mapper)
        existingAssignment.setAssignmentType(assignmentDTO.getAssignmentType());
        existingAssignment.setSubjectLevel(assignmentDTO.getSubjectLevel());
        existingAssignment.setSubject(assignmentDTO.getSubject());
        existingAssignment.setTitle(assignmentDTO.getTitle());
        existingAssignment.setDescription(assignmentDTO.getDescription());
        existingAssignment.setLocation(assignmentDTO.getLocation());
        existingAssignment.setByUser(assignmentDTO.getByUser());
        existingAssignment.setCreatedByUserId(assignmentDTO.getCreatedByUserId());
        existingAssignment.setTuitionDuration(assignmentDTO.getTuitionDuration());
        existingAssignment.setTuitionFrequencies(assignmentDTO.getTuitionFrequencies());
        existingAssignment.setPrice(assignmentDTO.getPrice());
        existingAssignment.setStatus(assignmentDTO.getStatus());
        existingAssignment.setCreatedDateTime(assignmentDTO.getCreatedDateTime());
        existingAssignment.setUpdatedDateTime(assignmentDTO.getUpdatedDateTime());
        existingAssignment.setExpirationDate(assignmentDTO.getExpirationDate());


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

    @Override
    public ServiceResponse deleteAssignment(Long assignmentId) {
        // Delete the assignment by ID using the repository
        assignmentRepository.deleteById(assignmentId);
        return new ServiceResponse("Assignment deleted successfully");
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
        assignmentDTO.setAssignmentType(assignment.getAssignmentType());
        assignmentDTO.setSubjectLevel(assignment.getSubjectLevel());
        assignmentDTO.setSubject(assignment.getSubject());
        assignmentDTO.setTitle(assignment.getTitle());
        assignmentDTO.setDescription(assignment.getDescription());
        assignmentDTO.setLocation(assignment.getLocation());
        assignmentDTO.setByUser(assignment.getByUser());
        assignmentDTO.setCreatedByUserId(assignment.getCreatedByUserId());
        assignmentDTO.setTuitionDuration(assignment.getTuitionDuration());
        assignmentDTO.setTuitionFrequencies(assignment.getTuitionFrequencies());
        assignmentDTO.setPrice(assignment.getPrice());
        assignmentDTO.setStatus(assignment.getStatus());
        assignmentDTO.setCreatedDateTime(assignment.getCreatedDateTime());
        assignmentDTO.setUpdatedDateTime(assignment.getUpdatedDateTime());
        assignmentDTO.setExpirationDate(assignment.getExpirationDate());

        return assignmentDTO;

        //shortcut
//        return assignmentMapper.assignmentToAssignmentDTO(assignment);
    }
    @Override
    public List<AssignmentDTO> getAssignmentRecommendations(Long assignmentId) {
        // Retrieve the target assignment
        AssignmentDTO targetAssignment = getAssignmentById(assignmentId);
        if (targetAssignment == null) {
            return new ArrayList<>(); // Handle the case where the target assignment is not found
        }
        // Retrieve all assignments from the database
        List<AssignmentDTO> allAssignments = getAllAssignments();
        // Use the RecommendationService to get recommendations for the assignment
        List<AssignmentDTO> recommendations = recommendationService.getRecommendationsByAssignmentId(assignmentId);

        return recommendations;
    }

    // Implement the getAllAssignments method to fetch all assignments
    public List<AssignmentDTO> getAllAssignments() {
        List<Assignment> assignmentEntities = assignmentRepository.findAll();
        return assignmentEntities.stream()
                .map(assignmentMapper::assignmentToAssignmentDTO)
                .collect(Collectors.toList());
    }

    // Define a method to check if the status transition is allowed
    private boolean isStatusTransitionAllowed(AssignmentStatus currentStatus, AssignmentStatus newStatus) {
        // Implement rules for status transitions here
        // Allow updates within the same status or transitions from OPEN to IN_PROGRESS
        // and from IN_PROGRESS to COMPLETED.
        return (currentStatus == newStatus) ||
                (currentStatus == AssignmentStatus.OPEN && newStatus == AssignmentStatus.IN_PROGRESS) ||
                (currentStatus == AssignmentStatus.IN_PROGRESS && newStatus == AssignmentStatus.COMPLETED);
    }

    //obsolete
//    @Override
//    public List<AssignmentDTO> getAssignmentsByType(AssignmentType type) {
//        // Use the repository to fetch assignments by status
//        List<Assignment> assignments = assignmentRepository.findByAssignmentType(type);
//
//        // Map the entities to DTOs and return the list
//        return assignments.stream()
//                .map(assignmentMapper::assignmentToAssignmentDTO)
//                .collect(Collectors.toList());
//    }

    @Override
    public List<AssignmentDTO> getAssignmentsByTypeAndSubjectsAndLevel(AssignmentType type,
                                                                       List<Subject> subjects, SubjectLevel subjectLevel) {
        // Retrieve assignments by type from the repository
        List<Assignment> assignmentsByType = assignmentRepository.findByAssignmentType(type);

        // Filter assignments by subjects and subjectLevel
        List<AssignmentDTO> filteredAssignments = assignmentsByType.stream()
                .filter(assignment -> subjects.contains(assignment.getSubject()) && subjectLevel.equals(assignment.getSubjectLevel()))
                .map(assignmentMapper::assignmentToAssignmentDTO)
                .collect(Collectors.toList());

        return filteredAssignments;
    }

    @Scheduled(fixedRate = 86400000) // Run once a day (24 hours)
    public void updateAssignmentStatusBasedOnExpiration() {
        List<Assignment> assignments = assignmentRepository.findAll();
        LocalDateTime currentDate = LocalDateTime.now();

        for (Assignment assignment : assignments) {
            if (assignment.getUpdatedDateTime() != null) {
                LocalDateTime expirationDate = assignment.getUpdatedDateTime().plusDays(90);
                if (currentDate.isAfter(expirationDate)) {
                    assignment.setStatus(AssignmentStatus.EXPIRED);
                }
            }
        }
    }



}
