package com.hireme.product.payment.service;

import com.hireme.product.payment.Entity.Payment;
import com.hireme.product.payment.dto.PaymentCheckout;
import com.hireme.product.payment.dto.PaymentDto;

import java.util.List;

public interface Service {
    String checkout(PaymentCheckout paymentCheckout);

    void successPayment(String sessionId);

    List<PaymentDto> getPastPayment(String userId);
}
