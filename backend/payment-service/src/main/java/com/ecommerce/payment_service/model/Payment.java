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
        CARD, UPI, WALLET, COD
    }

    public enum PaymentStatus {
        PENDING, SUCCESS, FAILED
    }

    // Helper method for validation
    public boolean isValid() {
        return orderId != null && paymentMethod != null && amount != null && amount.compareTo(BigDecimal.ZERO) > 0;
    }

    // Helper method to set initial status
    public void setInitialStatus() {
        this.status = PaymentStatus.PENDING;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public Long getOrderId() { return orderId; }
    public PaymentStatus getStatus() { return status; }
    public void setStatus(PaymentStatus status) { this.status = status; }
}

