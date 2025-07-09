package com.ecommerce.shipping_service.service;

import com.ecommerce.shipping_service.dto.ShippingDTO;
import java.util.List;

public interface ShippingService {
    ShippingDTO createShipping(ShippingDTO dto);
    ShippingDTO getShippingById(Long id);
    List<ShippingDTO> getAllShippings();
}
