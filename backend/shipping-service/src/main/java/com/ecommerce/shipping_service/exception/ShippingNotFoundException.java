package com.ecommerce.shipping_service.exception;

public class ShippingNotFoundException extends RuntimeException {
    public ShippingNotFoundException(String message) {
        super(message);
    }
}
