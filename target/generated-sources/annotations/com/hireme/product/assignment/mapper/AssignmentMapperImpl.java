package com.hireme.product.assignment.mapper;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.entity.Assignment;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",

    date = "2023-10-10T14:01:06+0800",

    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class AssignmentMapperImpl implements AssignmentMapper {

    @Override
    public AssignmentDTO assignmentToAssignmentDTO(Assignment assignment) {
        if ( assignment == null ) {
            return null;
        }

        AssignmentDTO assignmentDTO = new AssignmentDTO();

        assignmentDTO.setAssignmentId( assignment.getAssignmentId() );
        assignmentDTO.setTitle( assignment.getTitle() );
        assignmentDTO.setDescription( assignment.getDescription() );
        assignmentDTO.setLocation( assignment.getLocation() );
        assignmentDTO.setByUser( assignment.getByUser() );
        assignmentDTO.setTuitionDuration( assignment.getTuitionDuration() );
        assignmentDTO.setStatus( assignment.getStatus() );
        assignmentDTO.setCreatedDateTime( assignment.getCreatedDateTime() );

        return assignmentDTO;
    }

    @Override
    public Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }

        Assignment assignment = new Assignment();

        assignment.setAssignmentId( assignmentDTO.getAssignmentId() );
        assignment.setTitle( assignmentDTO.getTitle() );
        assignment.setDescription( assignmentDTO.getDescription() );
        assignment.setLocation( assignmentDTO.getLocation() );
        assignment.setByUser( assignmentDTO.getByUser() );
        assignment.setTuitionDuration( assignmentDTO.getTuitionDuration() );
        assignment.setStatus( assignmentDTO.getStatus() );
        assignment.setCreatedDateTime( assignmentDTO.getCreatedDateTime() );

        return assignment;
    }
}
