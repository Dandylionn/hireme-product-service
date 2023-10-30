package com.hireme.product.payment.service.impl;

import com.google.gson.Gson;
import com.hireme.product.assignment.entity.Assignment;
import com.hireme.product.assignment.enums.AssignmentStatus;
import com.hireme.product.assignment.repository.AssignmentRepository;
import com.hireme.product.payment.Entity.Payment;
import com.hireme.product.payment.dto.PaymentCheckout;
import com.hireme.product.payment.dto.PaymentDto;
import com.hireme.product.payment.enums.PaymentStatus;
import com.hireme.product.payment.mapper.PaymentMapper;
import com.hireme.product.payment.repository.PaymentRepository;
import com.hireme.product.payment.service.PaymentService;
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
import java.util.Optional;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@Transactional
public class PaymentServiceImpl implements PaymentService {
    private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
    private static Gson gson = new Gson();

    private final PaymentRepository paymentRepository;

    private final PaymentMapper paymentMapper;

    private final AssignmentRepository assignmentRepository;

    public PaymentServiceImpl(PaymentRepository paymentRepository, PaymentMapper paymentMapper, AssignmentRepository assignmentRepository) {
        this.paymentRepository = paymentRepository;
        this.paymentMapper = paymentMapper;
        this.assignmentRepository = assignmentRepository;
    }

    @Override
    public String checkout(PaymentCheckout paymentCheckout, String userId) {
        SessionCreateParams params = SessionCreateParams.builder()
                .addPaymentMethodType(SessionCreateParams.PaymentMethodType.CARD)
                .setMode(SessionCreateParams.Mode.PAYMENT)
                .setSuccessUrl(paymentCheckout.getSuccessUrl() + "?session_id={CHECKOUT_SESSION_ID}")
                .setCancelUrl(paymentCheckout.getCancelUrl())
                .setClientReferenceId(userId) // extra precautious to identify user
                .addLineItem(
                        SessionCreateParams.LineItem.builder().setQuantity(1L).setPriceData(
                                SessionCreateParams.LineItem.PriceData.builder().setCurrency(paymentCheckout.getCurrency() == null ? "sgd" : paymentCheckout.getCurrency())
                                        .setUnitAmount(paymentCheckout.getAmt()).setProductData(
                                                SessionCreateParams.LineItem.PriceData.ProductData.builder().setName(paymentCheckout.getProductName()).build()
                                ).build()
                        ).build()
                ).build();
        try {
            Session session = Session.create(params);
            logger.info("my session:" + session);
            Map<String, String> responseData = new HashMap<>();
            paymentRepository.save(createPaymentRecord(session, userId, paymentCheckout.getAssignmentId()));

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
    public String successPayment(String sessionId) {
        try {
             Session checkout = Session.retrieve(sessionId);
             if("paid".equals(checkout.getPaymentStatus())){
                 paymentRepository.getBySessionId(sessionId).ifPresentOrElse((payment) -> {
                     // normal flow
                     payment.setStatus(PaymentStatus.PAID);
                     paymentRepository.save(payment);

                     logger.info("saving assignment of id " + payment.getAssignmentId() + " to in progress");
                     Optional<Assignment> assignment = assignmentRepository.findByAssignmentId(Long.getLong(payment.getAssignmentId()));
                     assignment.ifPresent(temp -> {
                         temp.setStatus(AssignmentStatus.IN_PROGRESS);
                         assignmentRepository.save(temp);
                         logger.info("assignment status updated");
                     });
                 }, () -> {
                     // contingent, currently
                     logger.warn("Contingency hit for sessionid :" + sessionId);
                     Payment payment = new Payment();
                     payment.setSessionId(sessionId);
                     payment.setTotalPrice(checkout.getAmountTotal());
                     payment.setUserId(checkout.getClientReferenceId());
                     paymentRepository.save(payment);
                 });
                return "paid";
             }
        } catch (StripeException e) {
            e.printStackTrace();
        }
        return "not paid";
    }

    @Override
    public List<PaymentDto> getPastPayment(String userId) {
        Map<String, String> map = new HashMap<>();
        if(!StringUtils.isEmpty(userId) && !userId.trim().equals("")){
            return paymentRepository.getByUserIdOrderByDteTimeCrDesc(userId).stream().map(paymentMapper::toDto).collect(Collectors.toList());
        }
        return paymentRepository.findAllByOrderByDteTimeCrDesc().stream().map(paymentMapper::toDto).collect(Collectors.toList());
    }

    private Payment createPaymentRecord( Session checkout, String userid, String assignmentId){
        Payment payment = new Payment();
        payment.setSessionId(checkout.getId());
        payment.setTotalPrice(checkout.getAmountTotal());
        payment.setUserId(userid);
        payment.setStatus(PaymentStatus.NOT_PAID);
        payment.setAssignmentId(assignmentId);
        return payment;
    }
}
