package com.ecommerce.payment_service.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ecommerce.payment_service.model.Payment;
import com.ecommerce.payment_service.repository.PaymentRepository;

@Service
public class PaymentService {
    @Autowired
    private PaymentRepository paymentRepository;

    @Transactional
    public Payment processPayment(Payment payment) {
        payment.setInitialStatus();
        Payment savedPayment = paymentRepository.save(payment);
        // Here you would add logic to verify payment, interact with gateway, etc.
        // For demo, we just mark as SUCCESS
        savedPayment.setStatus(Payment.PaymentStatus.SUCCESS);
        paymentRepository.save(savedPayment);
        return savedPayment;
    }
}
