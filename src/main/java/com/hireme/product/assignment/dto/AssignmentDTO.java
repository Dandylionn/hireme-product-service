package com.hireme.product.assignment.dto;

import java.io.Serializable;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hireme.product.assignment.enums.*;
import com.hireme.product.assignment.serializers.LocalDateTimeSerializer;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Set;

@Getter
@Setter
public class AssignmentDTO implements Serializable {

    @JsonProperty("assignmentId")
    private Long assignmentId;

    @JsonProperty("assignmentType")
    private AssignmentType assignmentType;

    @JsonProperty("subjectLevel")
    private SubjectLevel subjectLevel;

    @JsonProperty("subject")
    private Subject subject;

    @JsonProperty("title")
    private String title;

    @JsonProperty("description")
    private String description;

    @JsonProperty("location")
    private String location;

    @JsonProperty("createdByUserId")
    private String createdByUserId;

    @JsonProperty("byUser")
    private String byUser;

    @JsonProperty("tuitionDuration")
    private String tuitionDuration;

    @JsonProperty("tuitionFrequencies")
    private Set<TuitionFrequency> tuitionFrequencies;

    @JsonProperty("price")
    private BigDecimal price;

    @JsonProperty("status")
    private AssignmentStatus status;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("createdDateTime")
    private LocalDateTime createdDateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("updatedDateTime")
    private LocalDateTime updatedDateTime;

    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @JsonProperty("expirationDate")
    private LocalDateTime expirationDate;

    public String formatCreatedDateTime() {
        if (createdDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return createdDateTime.format(formatter);
        }
        return null;
    }
    public String formatUpdatedDateTime() {
        if (updatedDateTime != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return updatedDateTime.format(formatter);
        }
        return null;
    }

    public String formatExpirationDate() {
        if (expirationDate != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return expirationDate.format(formatter);
        }
        return null;
    }





}

