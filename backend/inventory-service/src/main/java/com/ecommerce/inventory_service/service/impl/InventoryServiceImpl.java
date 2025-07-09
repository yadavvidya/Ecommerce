package com.ecommerce.inventory_service.service.impl;

import com.ecommerce.inventory_service.dto.InventoryDTO;
import com.ecommerce.inventory_service.exception.InventoryNotFoundException;
import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.InventoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class InventoryServiceImpl implements InventoryService {

    @Autowired
    private InventoryRepository repository;

    @Override
    public InventoryDTO getInventoryByProductId(Long productId) {
        Inventory inventory = repository.findByProductId(productId)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found for product ID " + productId));
        return mapToDTO(inventory);
    }

    @Override
    public List<InventoryDTO> getAllInventories() {
        return repository.findAll().stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public InventoryDTO createInventory(InventoryDTO dto) {
        Inventory inventory = mapToEntity(dto);
        inventory.setUpdatedAt(LocalDateTime.now());
        return mapToDTO(repository.save(inventory));
    }

    @Override
    public InventoryDTO updateInventory(Long id, InventoryDTO dto) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + id));
        inventory.setAvailableQty(dto.getAvailableQty());
        inventory.setReservedQty(dto.getReservedQty());
        inventory.setUpdatedAt(LocalDateTime.now());
        return mapToDTO(repository.save(inventory));
    }

    @Override
    public void deleteInventory(Long id) {
        Inventory inventory = repository.findById(id)
                .orElseThrow(() -> new InventoryNotFoundException("Inventory not found with id: " + id));
        repository.delete(inventory);
    }

    private InventoryDTO mapToDTO(Inventory inventory) {
        return InventoryDTO.builder()
                .id(inventory.getId())
                .productId(inventory.getProductId())
                .availableQty(inventory.getAvailableQty())
                .reservedQty(inventory.getReservedQty())
                .updatedAt(inventory.getUpdatedAt())
                .build();
    }

    private Inventory mapToEntity(InventoryDTO dto) {
        return Inventory.builder()
                .id(dto.getId())
                .productId(dto.getProductId())
                .availableQty(dto.getAvailableQty())
                .reservedQty(dto.getReservedQty())
                .updatedAt(dto.getUpdatedAt())
                .build();
    }
}
