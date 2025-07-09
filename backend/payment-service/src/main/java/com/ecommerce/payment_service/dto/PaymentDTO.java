package com.ecommerce.payment_service.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import com.ecommerce.payment_service.model.Payment.PaymentMethod;
import com.ecommerce.payment_service.model.Payment.PaymentStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PaymentDTO {
    private Long id;
    private Long orderId;
    private PaymentMethod paymentMethod;
    private PaymentStatus status;
    private String transactionId;
    private BigDecimal amount;
    private LocalDateTime paidAt;
}
