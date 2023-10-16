package com.hireme.product.assignment.repository;

import com.hireme.product.assignment.entity.Assignment;
import com.hireme.product.assignment.enums.AssignmentType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    List<Assignment> findByStatus(String status);
    List<Assignment> findByAssignmentType(AssignmentType type);
}
