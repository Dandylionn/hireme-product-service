package com.hireme.product.payment.repository;

import com.hireme.product.payment.Entity.Payment;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

@org.springframework.stereotype.Repository
public interface PaymentRepository extends JpaRepository<Payment, String> {
    Optional<Payment> getBySessionId(String sessionId);

    List<Payment> getByUserIdOrderByDteTimeCrDesc(String userId);

    List<Payment> findAllByOrderByDteTimeCrDesc();
}
