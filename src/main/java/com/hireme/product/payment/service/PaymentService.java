package com.hireme.product.payment.service;

import com.hireme.product.payment.dto.PaymentCheckout;
import com.hireme.product.payment.dto.PaymentDto;

import java.util.List;

public interface PaymentService {
    String checkout(PaymentCheckout paymentCheckout, String userId);

    void successPayment(String sessionId);

    List<PaymentDto> getPastPayment(String userId);
}
