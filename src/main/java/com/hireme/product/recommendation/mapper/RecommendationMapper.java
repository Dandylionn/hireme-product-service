package com.hireme.product.recommendation.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.hireme.product.recommendation.dto.RecommendationDTO;
import com.hireme.product.recommendation.entity.Recommendation;


@Mapper(
        componentModel = "spring", uses = {}
)
public interface RecommendationMapper{

    RecommendationMapper INSTANCE = Mappers.getMapper(RecommendationMapper.class);

    @Mapping(target = "recommendationId", source = "recommendationId")
    @Mapping(target = "requestId", source = "requestId")
    @Mapping(target = "assignmentId", source = "assignmentId")
    @Mapping(target = "relevanceScore", source = "relevanceScore")
    @Mapping(target = "createdAt", source = "createdAt")
    RecommendationDTO recommendationToRecommendationDTO(Recommendation recommendation);

    @Mapping(target = "recommendationId", source = "recommendationId")
    @Mapping(target = "requestId", source = "requestId")
    @Mapping(target = "assignmentId", source = "assignmentId")
    @Mapping(target = "relevanceScore", source = "relevanceScore")
    @Mapping(target = "createdAt", source = "createdAt")
    Recommendation RecommendationDTOToRecommendation(RecommendationDTO recommendationDTO);

}
