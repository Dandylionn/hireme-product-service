package com.hireme.product.payment.Resource;

import com.hireme.product.payment.dto.PaymentCheckout;
import com.hireme.product.payment.dto.PaymentDto;
import com.hireme.product.payment.service.Service;
import com.stripe.exception.StripeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payments")
public class PaymentResource {
    private final Service paymentService;

    Logger logger = LoggerFactory.getLogger(PaymentResource.class);

    public PaymentResource(Service paymentService) {
        this.paymentService = paymentService;
    }

    @PostMapping("/payment")
    public String createCheckoutSession( @RequestBody PaymentCheckout request){
        logger.info("In createCheckoutSession");
        return paymentService.checkout(request);
    }

    @PutMapping("save/{sessionId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void saveSuccessfulResource(@PathVariable String sessionId){
        logger.info("saving session id " + sessionId);
        paymentService.successPayment(sessionId);
    }

    @GetMapping("history/{userId}")
    public List<PaymentDto> getPastPayments(@PathVariable String userId){
        logger.info("geting historical payment");
        return paymentService.getPastPayment(userId);
    }
}
