package com.hireme.product.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

import java.sql.Timestamp;

@Getter
@Setter
public class PaymentDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty( "userId")
    private Long userId;

    @JsonProperty( "assignmentId")
    private Long assignmentId;

    @JsonProperty( "sessionId")
    private String sessionId;

    @JsonProperty( "totalPrice")
    private Long totalPrice;

    @JsonProperty( "dteTimeCr")
    private Timestamp dteTimeCr;

    @JsonProperty( "transactionDesc")
    private String transactionDesc;
}
