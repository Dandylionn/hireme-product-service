package com.hireme.product.assignment.service;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.enums.AssignmentType;
import com.hireme.product.assignment.enums.Subject;
import com.hireme.product.assignment.enums.SubjectLevel;
import com.hireme.product.assignment.repository.AssignmentRepository;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AssignmentService {
    @Autowired
//    private AssignmentRepository assignmentRepository;

    AssignmentDTO createAssignment(AssignmentDTO assignmentDTO);
    AssignmentDTO updateAssignment(Long assignmentId, AssignmentDTO assignmentDTO);
    ServiceResponse  deleteAssignment(Long assignmentId);
    AssignmentDTO getAssignmentById(Long assignmentId);
    List<AssignmentDTO> getAssignmentRecommendations(Long assignmentId);
    List<AssignmentDTO> getAllAssignments();

    //only assignment type
//    List<AssignmentDTO> getAssignmentsByType(AssignmentType type);

    List<AssignmentDTO> getAssignmentsByTypeAndSubjectsAndLevel(AssignmentType type,
                                                                List<Subject> subjects, SubjectLevel subjectLevel);



    //for deleteAssignment Message
    public class ServiceResponse {
        private String message;

        public ServiceResponse(String message) {
            this.message = message;
        }
        public String getMessage() {
            return message;
        }
        public void setMessage(String message) {
            this.message = message;
        }

    }


}
