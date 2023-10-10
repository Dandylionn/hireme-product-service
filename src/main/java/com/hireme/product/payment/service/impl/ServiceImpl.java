package com.hireme.product.payment.service.impl;

import com.google.gson.Gson;
import com.hireme.product.payment.Entity.Payment;
import com.hireme.product.payment.dto.PaymentCheckout;
import com.hireme.product.payment.dto.PaymentDto;
import com.hireme.product.payment.mapper.PaymentMapper;
import com.hireme.product.payment.repository.Repository;
import com.hireme.product.payment.service.Service;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import com.stripe.param.checkout.SessionCreateParams;
import jakarta.transaction.Transactional;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class ServiceImpl implements Service {
    private final Logger logger = LoggerFactory.getLogger(ServiceImpl.class);
    private static Gson gson = new Gson();

    private final Repository repository;

    private final PaymentMapper paymentMapper;

    public ServiceImpl(Repository repository, PaymentMapper paymentMapper) {
        this.repository = repository;
        this.paymentMapper = paymentMapper;
    }

    @Override
    public String checkout(PaymentCheckout paymentCheckout) {
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(paymentCheckout.getSuccessUrl() + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(paymentCheckout.getCancelUrl())
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder().setCurrency(paymentCheckout.getCurrency())
                                        .setUnitAmount(paymentCheckout.getAmt()).setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(paymentCheckout.getProductName()).build()
                                ).build()
                        ).build()
                ).build();
        try {
            Session session = Session.create(params);
            logger.info("my session:" + session);
            Map<String, String> responseData = new HashMap<>();
            // We get the sessionId and we putted inside the response data you can get more info from the session object
            responseData.put("id", session.getId());
            // We can return only the sessionId as a String
            return gson.toJson(responseData);
        } catch (StripeException e) {
            e.printStackTrace();
            logger.error(e.getMessage());
            throw new RuntimeException();
        }
    }

    @Override
    public void successPayment(String sessionId) {
        try {
             Session checkout = Session.retrieve(sessionId);
             if("paid".equals(checkout.getPaymentStatus())){
                 Payment payment = new Payment();
                 payment.setSessionId(sessionId);
                 payment.setTotalPrice(checkout.getAmountTotal());
                 payment.setUserId(-1L);
                 repository.getBySessionId(sessionId).ifPresentOrElse((session) -> {}, () -> {
                     repository.save(payment);
                 });
             }
        } catch (StripeException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<PaymentDto> getPastPayment(String userId) {
        Map<String, String> map = new HashMap<>();
        if(!StringUtils.isEmpty(userId) && !userId.trim().equals("")){
            return repository.getByUserIdOrderByDteTimeCrDesc(userId).stream().map(paymentMapper::toDto).collect(Collectors.toList());
        }
        return repository.findAllByOrderByDteTimeCrDesc().stream().map(paymentMapper::toDto).collect(Collectors.toList());
    }
}
