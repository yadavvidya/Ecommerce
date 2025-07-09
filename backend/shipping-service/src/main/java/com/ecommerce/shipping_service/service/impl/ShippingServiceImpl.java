package com.ecommerce.shipping_service.service.impl;

import com.ecommerce.shipping_service.dto.ShippingDTO;
import com.ecommerce.shipping_service.model.Shipping;
import com.ecommerce.shipping_service.repository.ShippingRepository;
import com.ecommerce.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ShippingServiceImpl implements ShippingService {

    private final ShippingRepository shippingRepository;

    @Override
    public ShippingDTO createShipping(ShippingDTO dto) {
        Shipping shipping = Shipping.builder()
                .orderId(dto.getOrderId())
                .trackingNumber(dto.getTrackingNumber())
                .status(dto.getStatus())
                .shippedAt(dto.getShippedAt())
                .deliveredAt(dto.getDeliveredAt())
                .address(dto.getAddress())
                .build();
        Shipping saved = shippingRepository.save(shipping);
        return toDTO(saved);
    }

    @Override
    public ShippingDTO getShippingById(Long id) {
        return shippingRepository.findById(id)
                .map(this::toDTO)
                .orElseThrow(() -> new RuntimeException("Shipping not found with id " + id));
    }

    @Override
    public List<ShippingDTO> getAllShippings() {
        return shippingRepository.findAll().stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }

    private ShippingDTO toDTO(Shipping shipping) {
        return ShippingDTO.builder()
                .id(shipping.getId())
                .orderId(shipping.getOrderId())
                .trackingNumber(shipping.getTrackingNumber())
                .status(shipping.getStatus())
                .shippedAt(shipping.getShippedAt())
                .deliveredAt(shipping.getDeliveredAt())
                .address(shipping.getAddress())
                .build();
    }
}
