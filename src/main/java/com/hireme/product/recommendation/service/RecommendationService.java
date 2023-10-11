package com.hireme.product.recommendation.service;

import com.hireme.product.assignment.dto.AssignmentDTO;
import com.hireme.product.recommendation.dto.RecommendationDTO;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public interface RecommendationService {

    public List<AssignmentDTO> getRecommendationsByAssignmentId(Long assignmentId);

}
