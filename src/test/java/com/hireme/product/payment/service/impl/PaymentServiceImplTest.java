package com.hireme.product.payment.service.impl;

import com.hireme.product.assignment.entity.Assignment;
import com.hireme.product.assignment.repository.AssignmentRepository;
import com.hireme.product.payment.Entity.Payment;
import com.hireme.product.payment.dto.PaymentCheckout;
import com.hireme.product.payment.dto.PaymentDto;
import com.hireme.product.payment.repository.PaymentRepository;
import com.stripe.exception.StripeException;
import com.stripe.model.checkout.Session;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
class PaymentServiceImplTest {

    @Mock
    private PaymentRepository paymentRepository;

    @Mock
    private AssignmentRepository assignmentRepository;

    @InjectMocks
    private PaymentServiceImpl paymentService;

    @Test
    void testCheckout() throws StripeException {
        // Mocked PaymentCheckout object and other necessary parameters
        PaymentCheckout paymentCheckout = new PaymentCheckout(/* Add necessary parameters */);
        paymentCheckout.setSuccessUrl("http://localhost:3000");
        paymentCheckout.setCancelUrl("http://localhost:3001");
        paymentCheckout.setAmt(50l);
        paymentCheckout.setProductName("dummy");

        String userId = "user123";


        // Mocking paymentRepository save method
        Mockito.when(paymentRepository.save(Mockito.any())).thenReturn(new Payment());

        // Perform the test
        String result = paymentService.checkout(paymentCheckout, userId);

        // Assertions or verifications
        // Add your assertions here according to the expected behavior
        Assertions.assertNotNull(result);
    }

    @Test
    void testSuccessPayment() throws StripeException {
        // Mocked sessionId
        String sessionId = "cs_test_a1bg60EitvxeifsAXoaZ2ji0LcZ0T3UMQCWIvKZjutPes03BaWYSNKpbMT";

        // Mocking Session and Checkout
        Session checkout = Mockito.mock(Session.class);


        // Mocking paymentRepository methods
        Mockito.when(paymentRepository.getBySessionId(sessionId)).thenReturn(Optional.of(new Payment()));

        // Mocking assignmentRepository methods
        Assignment assignment = new Assignment();
        Mockito.when(assignmentRepository.findByAssignmentId(Mockito.anyLong())).thenReturn(Optional.of(assignment));

        // Perform the test
        String pay = paymentService.successPayment(sessionId);

        // Assertions or verifications
        // Add your assertions or verifications here according to the expected behavior
        Assertions.assertEquals("paid", pay);
    }

    @Test
    void testGetPastPayment() {
        // Mocked userId
        String userId = "user123";

        // Mocking paymentRepository methods
        Mockito.when(paymentRepository.getByUserIdOrderByDteTimeCrDesc(userId)).thenReturn(new ArrayList<>());

        // Perform the test
        List<PaymentDto> pastPayments = paymentService.getPastPayment(userId);

        // Assertions or verifications
        // Add your assertions or verifications here according to the expected behavior
    }
}