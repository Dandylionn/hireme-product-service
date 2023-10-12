package com.hireme.product.recommendation.controller;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.recommendation.dto.RecommendationDTO;
import com.hireme.product.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public RecommendationController(RecommendationService recommendationService, JdbcTemplate jdbcTemplate) {
        this.recommendationService = recommendationService;
        this.jdbcTemplate = jdbcTemplate;
    }

    @GetMapping("/assignment/{assignmentId}")
    public ResponseEntity<Map<String, List<AssignmentDTO>>> getRecommendationsByAssignmentId(@PathVariable Long assignmentId) {
        List<AssignmentDTO> recommendations = recommendationService.getRecommendationsByAssignmentId(assignmentId);

        Map<String, List<AssignmentDTO>> response = new HashMap<>();
        response.put("recommendations", recommendations);

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
