package com.hireme.product.assignment.entity;

import com.hireme.product.assignment.enums.*;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Getter
@Setter
@Table(name = "assignment")
public class Assignment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Enumerated(EnumType.STRING)
    @Column(name = "assignment_type")
    private AssignmentType assignmentType;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject_level")
    private SubjectLevel subjectLevel;

    @Enumerated(EnumType.STRING)
    @Column(name = "subject")
    private Subject subject;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "created_by_user_id",
//            nullable = false,
            length = 45) // Store the user's ID or username
    private String createdByUserId;

    @Column(name = "by_user", nullable = false, length = 45)
    private String byUser;

    @Column(name = "tuition_duration")
    private String tuitionDuration;

    @ElementCollection(targetClass = TuitionFrequency.class)
    @CollectionTable(name = "tuition_frequencies", joinColumns = @JoinColumn(name = "assignment_id"))
    @Column(name = "tuition_frequency")
    @Enumerated(EnumType.STRING)
    private Set<TuitionFrequency> tuitionFrequencies;

    @Column(name = "price", precision = 10, scale = 2)  // Define the precision and scale for price
    private BigDecimal price;


    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;

    @Column(name = "createdDateTime")
    private LocalDateTime createdDateTime;

    @Column(name = "updatedDateTime")
    private LocalDateTime updatedDateTime;
}
