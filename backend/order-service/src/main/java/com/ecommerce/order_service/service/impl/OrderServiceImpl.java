package com.ecommerce.order_service.service.impl;

import com.ecommerce.order_service.dto.OrderDTO;
import com.ecommerce.order_service.dto.OrderItemDTO;
import com.ecommerce.order_service.exception.OrderNotFoundException;
import com.ecommerce.order_service.model.Order;
import com.ecommerce.order_service.model.OrderItem;
import com.ecommerce.order_service.repository.OrderRepository;
import com.ecommerce.order_service.service.OrderService;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;

    @Override
    public OrderDTO placeOrder(OrderDTO dto) {
        Order order = Order.builder()
                .userId(dto.getUserId())
                .orderStatus(dto.getOrderStatus())
                .totalAmount(dto.getTotalAmount())
                .createdAt(LocalDateTime.now())
                .build();

        List<OrderItem> items = dto.getItems().stream().map(item -> OrderItem.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .order(order)
                .build()).collect(Collectors.toList());

        order.setItems(items);
        Order saved = orderRepository.save(order);

        return convertToDTO(saved);
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        return orderRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(Long id) {
        Order order = orderRepository.findById(id).orElseThrow(() ->
                new OrderNotFoundException("Order not found with ID: " + id));
        return convertToDTO(order);
    }

    private OrderDTO convertToDTO(Order order) {
        List<OrderItemDTO> itemDTOs = order.getItems().stream().map(item -> OrderItemDTO.builder()
                .productId(item.getProductId())
                .quantity(item.getQuantity())
                .price(item.getPrice())
                .build()).collect(Collectors.toList());

        return OrderDTO.builder()
                .id(order.getId())
                .userId(order.getUserId())
                .orderStatus(order.getOrderStatus())
                .totalAmount(order.getTotalAmount())
                .createdAt(order.getCreatedAt())
                .items(itemDTOs)
                .build();
    }
}