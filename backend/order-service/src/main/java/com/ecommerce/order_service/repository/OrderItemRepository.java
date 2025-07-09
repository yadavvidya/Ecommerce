package com.ecommerce.order_service.repository;


import org.springframework.data.jpa.repository.JpaRepository;

import com.ecommerce.order_service.model.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}
