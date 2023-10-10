package com.hireme.product.assignment.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hireme.product.assignment.enums.AssignmentStatus;
import com.hireme.product.assignment.serializers.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
public class AssignmentDTO implements Serializable {

    @JsonProperty("assignmentId")
    private Long assignmentId;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location")
    private String location;

    @JsonProperty("byUser")
    private String byUser;

//    @JsonProperty("createdByUserId")
//    private String a;

    @JsonProperty("tuitionDuration")
    private int tuitionDuration;

    @JsonProperty("status")
    private AssignmentStatus status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("createdDateTime")
    private LocalDateTime createdDateTime;

    public String formatCreatedDateTime() {
        if (createdDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return createdDateTime.format(formatter);
        }
        return null;
    }
}
