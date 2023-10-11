package com.hireme.product.recommendation.mapper;

import com.hireme.product.recommendation.dto.RecommendationDTO;
import com.hireme.product.recommendation.entity.Recommendation;
import javax.annotation.processing.Generated;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",

    date = "2023-10-10T14:01:06+0800",

    comments = "version: 1.5.3.Final, compiler: javac, environment: Java 17.0.2 (Oracle Corporation)"
)
@Component
public class RecommendationMapperImpl implements RecommendationMapper {

    @Override
    public RecommendationDTO recommendationToRecommendationDTO(Recommendation recommendation) {
        if ( recommendation == null ) {
            return null;
        }

        RecommendationDTO recommendationDTO = new RecommendationDTO();

        if ( recommendation.getRecommendationId() != null ) {
            recommendationDTO.setRecommendationId( recommendation.getRecommendationId().intValue() );
        }
        recommendationDTO.setRequestId( recommendation.getRequestId() );
        recommendationDTO.setAssignmentId( recommendation.getAssignmentId() );
        recommendationDTO.setRelevanceScore( recommendation.getRelevanceScore() );
        recommendationDTO.setCreatedAt( recommendation.getCreatedAt() );

        return recommendationDTO;
    }

    @Override
    public Recommendation RecommendationDTOToRecommendation(RecommendationDTO recommendationDTO) {
        if ( recommendationDTO == null ) {
            return null;
        }

        String requestId = null;
        Long assignmentId = null;
        Double relevanceScore = null;

        requestId = recommendationDTO.getRequestId();
        assignmentId = recommendationDTO.getAssignmentId();
        relevanceScore = recommendationDTO.getRelevanceScore();

        Recommendation recommendation = new Recommendation( requestId, assignmentId, relevanceScore );

        recommendation.setRecommendationId( (long) recommendationDTO.getRecommendationId() );
        recommendation.setCreatedAt( recommendationDTO.getCreatedAt() );

        return recommendation;
    }
}
