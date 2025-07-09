package com.ecommerce.product_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.product_service.model.Product;

public interface ProductRepository extends JpaRepository<Product, Long> {
    boolean existsBySku(String sku);
}

