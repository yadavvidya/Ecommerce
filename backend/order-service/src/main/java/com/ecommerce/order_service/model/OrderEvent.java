package com.ecommerce.order_service.model;

public class OrderEvent {
    private Long orderId;
    private String eventType; // OrderPlaced, OrderConfirmed, OrderFailed, OrderShipped
    private String status;
    private String message;

    public OrderEvent(Long orderId, String eventType, String status, String message) {
        this.orderId = orderId;
        this.eventType = eventType;
        this.status = status;
        this.message = message;
    }

    // Getters and Setters
    public Long getOrderId() { return orderId; }
    public void setOrderId(Long orderId) { this.orderId = orderId; }
    public String getEventType() { return eventType; }
    public void setEventType(String eventType) { this.eventType = eventType; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
    public String getMessage() { return message; }
    public void setMessage(String message) { this.message = message; }
}
