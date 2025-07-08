package com.ecommerce.order_service.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderStatus;
import com.ecommerce.order_service.repository.OrderRepository;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    private final String INVENTORY_SERVICE_URL = "http://localhost:8082/inventory/reserve"; // Adjust port as needed
    private final RestTemplate restTemplate = new RestTemplate();

    @Transactional
    public Order placeOrder(Order order) {
        order.setOrderStatus(OrderStatus.PENDING);
        order.setCreatedAt(java.time.LocalDateTime.now());
        Order savedOrder = orderRepository.save(order);
        // Call Inventory Service to reserve stock
        try {
            ResponseEntity<String> response = restTemplate.postForEntity(
                INVENTORY_SERVICE_URL,
                java.util.Map.of("productId", order.getProducts().get(0), "quantity", 1), // Simplified: 1st product, qty 1
                String.class
            );
            if (response.getStatusCode() == HttpStatus.OK) {
                savedOrder.setOrderStatus(OrderStatus.PROCESSING);
            } else {
                savedOrder.setOrderStatus(OrderStatus.FAILED);
            }
        } catch (Exception e) {
            savedOrder.setOrderStatus(OrderStatus.FAILED);
        }
        return orderRepository.save(savedOrder);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public Order getOrderById(Long id) {
        return orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
    }

    @Transactional
    public Order updateOrderStatus(Long id, String status) {
        Order order = getOrderById(id);
        order.setOrderStatus(OrderStatus.valueOf(status.toUpperCase()));
        return orderRepository.save(order);
    }
}
