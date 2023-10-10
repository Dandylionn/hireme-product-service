package com.hireme.product.assignment.repository;

import com.hireme.product.assignment.entity.Assignment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AssignmentRepository extends JpaRepository<Assignment, Long> {
    // Custom query method to find assignments by a specific status
    List<Assignment> findByStatus(String status);

    // Add more custom query methods as needed.
}
