package com.ecommerce.order_service.model;

public enum OrderStatus {
    PENDING,
    PROCESSING,
    SHIPPED,
    DELIVERED,
    CANCELLED,
    FAILED // Added missing status
}
