package com.ecommerce.product_service.dto;


import java.math.BigDecimal;

public class ProductRequest {
    private String name;
    private String description;
    private BigDecimal price;
    private String sku;
    private String category;
    private String imageUrl;
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public void setPrice(BigDecimal price) {
        this.price = price;
    }
    public void setSku(String sku) {
        this.sku = sku;
    }
    public void setCategory(String category) {
        this.category = category;
    }
    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }
    public String getDescription() {
        return description;
    }
    public BigDecimal getPrice() {
        return price;
    }
    public String getSku() {
        return sku;
    }
    public String getCategory() {
        return category;
    }
    public String getImageUrl() {
        return imageUrl;
    }

    
}

