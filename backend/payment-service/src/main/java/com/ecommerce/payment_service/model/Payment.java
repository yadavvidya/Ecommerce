package com.ecommerce.payment_service.model;


import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "payments")
public class Payment {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;

    @Enumerated(EnumType.STRING)
    private PaymentMethod paymentMethod;

    @Enumerated(EnumType.STRING)
    private PaymentStatus status;

    private String transactionId;
    private BigDecimal amount;
    private LocalDateTime paidAt;

    public enum PaymentMethod {
        CARD, UPI, COD
    }

    public enum PaymentStatus {
        SUCCESS, FAILED
    }

    // Getters and Setters
}

