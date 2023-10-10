package com.hireme.product.recommendation.service;

import com.hireme.product.recommendation.dto.RecommendationDTO;
import com.hireme.product.recommendation.entity.Recommendation;
import com.hireme.product.recommendation.mapper.RecommendationMapper;
import com.hireme.product.recommendation.repository.RecommendationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.hireme.product.assignment.dto.AssignmentDTO;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class RecommendationService {
    @Autowired
    private RecommendationRepository recommendationRepository;

    @Autowired
    private RecommendationMapper recommendationMapper; // Inject your Mapper

    public RecommendationDTO createRecommendation(String requestId, Long assignmentId, Double relevanceScore) {
        Recommendation recommendation = new Recommendation(requestId, assignmentId, relevanceScore);
        Recommendation savedRecommendation = recommendationRepository.save(recommendation);
        return recommendationMapper.recommendationToRecommendationDTO(savedRecommendation);
    }

    public List<RecommendationDTO> getAllRecommendations() {
        List<Recommendation> recommendations = recommendationRepository.findAll();
        return recommendations.stream()
                .map(recommendationMapper::recommendationToRecommendationDTO)
                .collect(Collectors.toList());
    }

    public List<RecommendationDTO> getRecommendationsByRequestId(String requestId) {
        List<Recommendation> recommendations = recommendationRepository.findByRequestId(requestId);
        return recommendations.stream()
                .map(recommendationMapper::recommendationToRecommendationDTO)
                .collect(Collectors.toList());
    }

    public List<RecommendationDTO> getRecommendationsByAssignmentId(Long assignmentId) {
        List<Recommendation> recommendations = recommendationRepository.findByAssignmentId(assignmentId);
        return recommendations.stream()
                .map(recommendationMapper::recommendationToRecommendationDTO)
                .collect(Collectors.toList());
    }


//    public List<AssignmentDTO> getContentBasedRecommendations(String userId) {
//        // Fetch the user's interaction history (e.g., assignments they viewed or liked)
//        List<AssignmentDTO> userHistory = getUserHistory(userId);
//
//        // Extract features from assignments (e.g., titles and descriptions)
//        List<AssignmentFeatures> assignmentFeatures = extractAssignmentFeatures();
//
//        // Calculate similarities between user history and assignments
//        List<AssignmentSimilarity> assignmentSimilarities = calculateSimilarities(userHistory, assignmentFeatures);
//
//        // Sort assignments by similarity score (descending)
//        Collections.sort(assignmentSimilarities, Collections.reverseOrder());
//
//        // Get the top N recommendations
//        List<AssignmentDTO> recommendations = getTopNRecommendations(assignmentSimilarities, N);
//
//        return recommendations;
}
