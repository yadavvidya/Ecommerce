package com.ecommerce.payment_service.model;

public class PaymentEvent {
    private Long paymentId;
    private Long orderId;
    private String eventType; // PaymentInitiated, PaymentSuccess, PaymentFailed
    private String status;
    private String message;

    public PaymentEvent(Long paymentId, Long orderId, String eventType, String status, String message) {
        this.paymentId = paymentId;
        this.orderId = orderId;
        this.eventType = eventType;
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public Long getPaymentId() { return paymentId; }
    public void setPaymentId(Long paymentId) { this.paymentId = paymentId; }
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
