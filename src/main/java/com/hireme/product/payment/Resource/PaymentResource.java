package com.hireme.product.payment.Resource;

import com.hireme.product.payment.dto.PaymentCheckout;
import com.hireme.product.payment.dto.PaymentDto;
import com.hireme.product.payment.service.PaymentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/payments")
public class PaymentResource {
    private final PaymentService paymentService;

    Logger logger = LoggerFactory.getLogger(PaymentResource.class);

    public PaymentResource(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    // used during checkout. The session id returned is for stripe purpose
    @Operation(
            summary = "Create a new payment session"
    )
    @ApiResponse(
            responseCode = "201",
            description = "session created successfully"
    )
    @PostMapping("/payment")
    public String createCheckoutSession( @RequestHeader("USER-ID") String userId,@RequestBody PaymentCheckout request){
        logger.info("In createCheckoutSession");
        return paymentService.checkout(request, userId);
    }

    // once payment is successful, call this endpoint
    @Operation(
            summary = "Check payment"
    )
    @ApiResponse(
            responseCode = "201",
            description = "payment is successful"
    )
    @PutMapping("save/{sessionId}")
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    public void saveSuccessfulResource(@RequestHeader("USER-ID") String userId, @PathVariable String sessionId){
        logger.info("saving session id " + sessionId);
        paymentService.successPayment(sessionId);
    }

    @Operation(
            summary = "payemnt history"
    )
    @ApiResponse(
            responseCode = "201",
            description = "history return"
    )
    @GetMapping("history/{userId}")
    public List<PaymentDto> getPastPayments(@RequestHeader("USER-ID") String userHdrId, @PathVariable String userId){
        logger.info("geting historical payment");
        return paymentService.getPastPayment(userId);
    }
}
