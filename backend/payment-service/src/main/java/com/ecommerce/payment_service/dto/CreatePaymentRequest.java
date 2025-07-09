package com.ecommerce.payment_service.dto;

import java.math.BigDecimal;

import com.ecommerce.payment_service.model.Payment.PaymentMethod;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreatePaymentRequest {
    private Long orderId;
    private PaymentMethod paymentMethod;
    private String transactionId;
    private BigDecimal amount;
}
