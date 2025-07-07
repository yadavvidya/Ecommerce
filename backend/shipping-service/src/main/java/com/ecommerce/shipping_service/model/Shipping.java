package com.ecommerce.shipping_service.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "shipping")
public class Shipping {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long orderId;
    private String trackingNumber;

    @Enumerated(EnumType.STRING)
    private ShippingStatus status;

    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private String address;

    public enum ShippingStatus {
        PENDING, SHIPPED, DELIVERED
    }

    // Getters and Setters
}
