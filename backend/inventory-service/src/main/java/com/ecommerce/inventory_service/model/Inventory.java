package com.ecommerce.inventory_service.model;


import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "inventory")
public class Inventory {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long productId;
    private Integer availableQty;
    private Integer reservedQty;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Reserve stock for an order
    public boolean reserveStock(int qty) {
        if (availableQty != null && availableQty >= qty) {
            availableQty -= qty;
            reservedQty = (reservedQty == null ? 0 : reservedQty) + qty;
            updatedAt = LocalDateTime.now();
            return true;
        }
        return false;
    }

    // Release reserved stock (e.g., on order cancel/fail)
    public void releaseStock(int qty) {
        reservedQty = (reservedQty == null ? 0 : reservedQty) - qty;
        availableQty = (availableQty == null ? 0 : availableQty) + qty;
        updatedAt = LocalDateTime.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
    public Integer getAvailableQty() { return availableQty; }
    public void setAvailableQty(Integer availableQty) { this.availableQty = availableQty; }
    public Integer getReservedQty() { return reservedQty; }
    public void setReservedQty(Integer reservedQty) { this.reservedQty = reservedQty; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
    public void setUpdatedAt(LocalDateTime updatedAt) { this.updatedAt = updatedAt; }
}
