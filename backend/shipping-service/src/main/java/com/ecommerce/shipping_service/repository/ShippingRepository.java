package com.ecommerce.shipping_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.shipping_service.model.Shipping;

public interface ShippingRepository extends JpaRepository<Shipping, Long> {
}
