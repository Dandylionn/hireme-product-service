package com.hireme.product.payment.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
public class PaymentDto {

    @JsonProperty("id")
    private Long id;

    @JsonProperty( "userId")
    private Long userId;

    @JsonProperty( "sessionId")
    private String sessionId;

    @JsonProperty( "totalPrice")
    private Long totalPrice;

    @JsonProperty( "dteTimeCr")
    private Timestamp dteTimeCr;

    @JsonProperty( "transactionDesc")
    private String transactionDesc;
}
