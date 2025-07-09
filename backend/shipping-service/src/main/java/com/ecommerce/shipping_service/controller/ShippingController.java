package com.ecommerce.shipping_service.controller;

import com.ecommerce.shipping_service.dto.ShippingDTO;
import com.ecommerce.shipping_service.service.ShippingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/shippings")
@RequiredArgsConstructor
public class ShippingController {

    private final ShippingService shippingService;

    @PostMapping
    public ResponseEntity<ShippingDTO> createShipping(@RequestBody ShippingDTO dto) {
        return ResponseEntity.ok(shippingService.createShipping(dto));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ShippingDTO> getById(@PathVariable Long id) {
        return ResponseEntity.ok(shippingService.getShippingById(id));
    }

    @GetMapping
    public ResponseEntity<List<ShippingDTO>> getAll() {
        return ResponseEntity.ok(shippingService.getAllShippings());
    }
}
