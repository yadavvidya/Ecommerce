package com.ecommerce.payment_service.service;

import java.util.List;

import com.ecommerce.payment_service.dto.CreatePaymentRequest;
import com.ecommerce.payment_service.dto.PaymentDTO;

public interface PaymentService {
    PaymentDTO createPayment(CreatePaymentRequest request);
    PaymentDTO getPaymentById(Long id);
    List<PaymentDTO> getAllPayments();
}
