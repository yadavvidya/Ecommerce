package com.ecommerce.shipping_service.dto;

import com.ecommerce.shipping_service.model.Shipping.ShippingStatus;
import lombok.*;

import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ShippingDTO {
    private Long id;
    private Long orderId;
    private String trackingNumber;
    private ShippingStatus status;
    private LocalDateTime shippedAt;
    private LocalDateTime deliveredAt;
    private String address;
}
