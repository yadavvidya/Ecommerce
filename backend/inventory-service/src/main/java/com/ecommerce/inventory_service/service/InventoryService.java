package com.ecommerce.inventory_service.service;

import java.util.List;

import com.ecommerce.inventory_service.dto.InventoryDTO;

public interface InventoryService {
    InventoryDTO getInventoryByProductId(Long productId);
    List<InventoryDTO> getAllInventories();
    InventoryDTO createInventory(InventoryDTO inventoryDTO);
    InventoryDTO updateInventory(Long id, InventoryDTO inventoryDTO);
    void deleteInventory(Long id);
}
