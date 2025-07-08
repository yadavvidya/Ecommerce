package com.ecommerce.inventory_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;

@Service
public class InventoryService {
    @Autowired
    private InventoryRepository inventoryRepository;

    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    public Optional<Inventory> getInventoryByProductId(Long productId) {
        return inventoryRepository.findAll().stream()
                .filter(inv -> inv.getProductId().equals(productId))
                .findFirst();
    }

    public Inventory reserveInventory(Long productId, int qty) {
        Inventory inventory = getInventoryByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        if (inventory.reserveStock(qty)) {
            return inventoryRepository.save(inventory);
        } else {
            throw new RuntimeException("Not enough stock");
        }
    }

    public Inventory releaseInventory(Long productId, int qty) {
        Inventory inventory = getInventoryByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.releaseStock(qty);
        return inventoryRepository.save(inventory);
    }

    public Inventory updateInventory(Long productId, int availableQty) {
        Inventory inventory = getInventoryByProductId(productId)
                .orElseThrow(() -> new RuntimeException("Inventory not found"));
        inventory.setAvailableQty(availableQty);
        return inventoryRepository.save(inventory);
    }

    public List<Inventory> getLowStockAlerts(int threshold) {
        return inventoryRepository.findAll().stream()
                .filter(inv -> inv.getAvailableQty() != null && inv.getAvailableQty() <= threshold)
                .toList();
    }
}
