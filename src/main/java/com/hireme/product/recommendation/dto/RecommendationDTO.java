package com.hireme.product.recommendation.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class RecommendationDTO {

    @JsonProperty("recommendationId")
    private int recommendationId;

    @JsonProperty("requestId")
    private String requestId;

    @JsonProperty("assignmentId")
    private Long assignmentId;

    @JsonProperty("relevanceScore")
    private Double relevanceScore;

    @JsonProperty("createdAt")
    private LocalDateTime createdAt;

    public String formatCreatedAt() {
        if (createdAt != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            return createdAt.format(formatter);
        }
        return null;
    }

}

