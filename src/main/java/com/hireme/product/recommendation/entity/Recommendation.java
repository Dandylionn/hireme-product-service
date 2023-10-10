package com.hireme.product.recommendation.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
@Entity
@Getter
@Setter
@Table(name = "recommendation")
public class Recommendation implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "recommendation_id")
    private Long recommendationId;

    @Column(name = "request_id", nullable = false, length = 100)
    private String requestId;

    @Column(name = "assignment_id", nullable = false)
    private Long assignmentId;

    @Column(name = "relevance_score", nullable = false)
    private Double relevanceScore;

    @Column(name = "created_at", nullable = false)
    private LocalDateTime createdAt;

    public Recommendation(String requestId, Long assignmentId, Double relevanceScore) {
        this.requestId = requestId;
        this.assignmentId = assignmentId;
        this.relevanceScore = relevanceScore;
        this.createdAt = LocalDateTime.now();
    }


}
