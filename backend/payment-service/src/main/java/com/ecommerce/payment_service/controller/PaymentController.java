package com.ecommerce.payment_service.controller;

import com.ecommerce.payment_service.model.Payment;
import com.ecommerce.payment_service.service.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/payments")
public class PaymentController {
    @Autowired
    private PaymentService paymentService;

    @PostMapping
    public ResponseEntity<Payment> processPayment(@RequestBody Payment payment) {
        Payment savedPayment = paymentService.processPayment(payment);
        return ResponseEntity.ok(savedPayment);
    }
}
