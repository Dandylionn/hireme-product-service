package com.hireme.product.assignment.mapper;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.entity.Assignment;
import com.hireme.product.assignment.enums.TuitionFrequency;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-10-16T13:29:16+0800",
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
        assignmentDTO.setAssignmentType( assignment.getAssignmentType() );
        assignmentDTO.setSubjectLevel( assignment.getSubjectLevel() );
        assignmentDTO.setSubject( assignment.getSubject() );
        assignmentDTO.setTitle( assignment.getTitle() );
        assignmentDTO.setDescription( assignment.getDescription() );
        assignmentDTO.setLocation( assignment.getLocation() );
        assignmentDTO.setCreatedByUserId( assignment.getCreatedByUserId() );
        assignmentDTO.setByUser( assignment.getByUser() );
        assignmentDTO.setTuitionDuration( assignment.getTuitionDuration() );
        Set<TuitionFrequency> set = assignment.getTuitionFrequencies();
        if ( set != null ) {
            assignmentDTO.setTuitionFrequencies( new LinkedHashSet<TuitionFrequency>( set ) );
        }
        assignmentDTO.setPrice( assignment.getPrice() );
        assignmentDTO.setStatus( assignment.getStatus() );
        assignmentDTO.setCreatedDateTime( assignment.getCreatedDateTime() );
        assignmentDTO.setUpdatedDateTime( assignment.getUpdatedDateTime() );
        assignmentDTO.setExpirationDate( assignment.getExpirationDate() );

        return assignmentDTO;
    }

    @Override
    public Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO) {
        if ( assignmentDTO == null ) {
            return null;
        }

        Assignment assignment = new Assignment();

        assignment.setAssignmentId( assignmentDTO.getAssignmentId() );
        assignment.setAssignmentType( assignmentDTO.getAssignmentType() );
        assignment.setSubjectLevel( assignmentDTO.getSubjectLevel() );
        assignment.setSubject( assignmentDTO.getSubject() );
        assignment.setTitle( assignmentDTO.getTitle() );
        assignment.setDescription( assignmentDTO.getDescription() );
        assignment.setLocation( assignmentDTO.getLocation() );
        assignment.setCreatedByUserId( assignmentDTO.getCreatedByUserId() );
        assignment.setByUser( assignmentDTO.getByUser() );
        assignment.setTuitionDuration( assignmentDTO.getTuitionDuration() );
        Set<TuitionFrequency> set = assignmentDTO.getTuitionFrequencies();
        if ( set != null ) {
            assignment.setTuitionFrequencies( new LinkedHashSet<TuitionFrequency>( set ) );
        }
        assignment.setPrice( assignmentDTO.getPrice() );
        assignment.setStatus( assignmentDTO.getStatus() );
        assignment.setCreatedDateTime( assignmentDTO.getCreatedDateTime() );
        assignment.setUpdatedDateTime( assignmentDTO.getUpdatedDateTime() );
        assignment.setExpirationDate( assignmentDTO.getExpirationDate() );

        return assignment;
    }
}
