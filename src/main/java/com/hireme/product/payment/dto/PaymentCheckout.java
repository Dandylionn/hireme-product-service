package com.hireme.product.payment.dto;


import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class PaymentCheckout {

    private String productName;
    private String currency;
    private String successUrl;
    private String cancelUrl;
    private Long amt;

    private String assignmentId;
}
