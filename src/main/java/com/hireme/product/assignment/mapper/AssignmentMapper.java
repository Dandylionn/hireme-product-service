package com.hireme.product.assignment.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.assignment.entity.Assignment;


@Mapper(
        componentModel = "spring", uses = {}
)
public interface AssignmentMapper{
    AssignmentMapper INSTANCE = Mappers.getMapper(AssignmentMapper.class);

    @Mapping(target = "assignmentId", source = "assignmentId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "byUser", source = "byUser")
    @Mapping(target = "createdByUserId", source = "createdByUserId")
    @Mapping(target = "tuitionDuration", source = "tuitionDuration")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdDateTime", source = "createdDateTime")
    AssignmentDTO assignmentToAssignmentDTO(Assignment assignment);

    @Mapping(target = "assignmentId", source = "assignmentId")
    @Mapping(target = "title", source = "title")
    @Mapping(target = "description", source = "description")
    @Mapping(target = "location", source = "location")
    @Mapping(target = "byUser", source = "byUser")
    @Mapping(target = "createdByUserId", source = "createdByUserId")
    @Mapping(target = "tuitionDuration", source = "tuitionDuration")
    @Mapping(target = "status", source = "status")
    @Mapping(target = "createdDateTime", source = "createdDateTime")
    Assignment assignmentDTOToAssignment(AssignmentDTO assignmentDTO);

}