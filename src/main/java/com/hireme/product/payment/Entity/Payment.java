package com.hireme.product.payment.Entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.sql.Timestamp;

@Getter
@Setter
@NoArgsConstructor
@ToString
@EqualsAndHashCode(callSuper = false)
@Entity(name = "Payment")
@Table(name = "TBL_PAYMENT")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Column(name = "USER_ID")
    private Long userId;

    @Column(name = "SESSION_ID")
    private String sessionId;

    @Column(name = "TOTAL_PRICE")
    private Long totalPrice;

    @Column(name = "DTE_TIME_CR")
    @CreationTimestamp
    private Timestamp dteTimeCr;

    @Column(name = "TRANSACTION_DESCRIPTION")
    private String transactionDesc;
}
