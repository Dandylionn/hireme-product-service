package com.hireme.product.assignment.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import com.hireme.product.assignment.enums.AssignmentStatus;
@Entity
@Getter
@Setter
@Table(name = "assignment")
public class Assignment implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "assignment_id")
    private Long assignmentId;

    @Column(name = "title", nullable = false, length = 100)
    private String title;

    @Column(name = "description", nullable = false, length = 45)
    private String description;

    @Column(name = "location", nullable = false, length = 100)
    private String location;

    @Column(name = "createdByUserId",
//            nullable = false,
            length = 45) // Store the user's ID or username
    private String createdByUserId;

    @Column(name = "by_user", nullable = false, length = 45)
    private String byUser;

    @Column(name = "tuition_duration")
    private int tuitionDuration;

    @Column(name = "createddatetime")
    private LocalDateTime createdDateTime;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private AssignmentStatus status;
}
