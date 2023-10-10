package com.hireme.product.recommendation.controller;

import com.hireme.product.recommendation.service.RecommendationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/recommendations")
public class RecommendationController {
    private final RecommendationService recommendationService;
    private final JdbcTemplate jdbcTemplate; // Add JdbcTemplate

    @Autowired
    private RestTemplate restTemplate;
    @Autowired
    public RecommendationController(RecommendationService recommendationService, JdbcTemplate jdbcTemplate) { // Add JdbcTemplate to constructor
        this.recommendationService = recommendationService;
        this.jdbcTemplate = jdbcTemplate; // Initialize JdbcTemplate
    }


}