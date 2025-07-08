package com.ecommerce.inventory_service.controller;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventory_service.dto.InventoryRequest;
import com.ecommerce.inventory_service.model.Inventory;
import com.ecommerce.inventory_service.repository.InventoryRepository;
import com.ecommerce.inventory_service.service.InventoryService;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/inventory")
public class InventoryController {
    @Autowired
    private InventoryRepository inventoryRepository;
    @Autowired
    private InventoryService inventoryService;

    @GetMapping
    public List<Inventory> getAllInventory() {
        return inventoryRepository.findAll();
    }

    @PostMapping
    public Inventory addInventory(@RequestBody InventoryRequest request) {
        Inventory inventory = new Inventory();
        inventory.setProductId(request.getProductId());
        inventory.setAvailableQty(request.getAvailableQty());
        inventory.setReservedQty(request.getReservedQty() != null ? request.getReservedQty() : 0);
        inventory.setCreatedAt(request.getCreatedAt() != null ? request.getCreatedAt() : java.time.LocalDateTime.now());
        inventory.setUpdatedAt(request.getUpdatedAt() != null ? request.getUpdatedAt() : java.time.LocalDateTime.now());
        return inventoryRepository.save(inventory);
    }

    // Reserve inventory for an order
    @PostMapping("/reserve")
    public ResponseEntity<String> reserveInventory(@RequestBody Map<String, Object> payload) {
        Long productId = Long.valueOf(payload.get("productId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        Optional<Inventory> inventoryOpt = inventoryRepository.findAll().stream()
            .filter(inv -> inv.getProductId().equals(productId)).findFirst();
        if (inventoryOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Product not found");
        }
        Inventory inventory = inventoryOpt.get();
        if (inventory.getAvailableQty() == null || inventory.getAvailableQty() < quantity) {
            return ResponseEntity.status(409).body("Not enough stock");
        }
        inventory.setAvailableQty(inventory.getAvailableQty() - quantity);
        inventory.setReservedQty((inventory.getReservedQty() == null ? 0 : inventory.getReservedQty()) + quantity);
        inventory.setUpdatedAt(java.time.LocalDateTime.now());
        inventoryRepository.save(inventory);
        return ResponseEntity.ok("Stock reserved");
    }

    // Release reserved inventory (e.g., on order cancel/fail)
    @PostMapping("/release")
    public ResponseEntity<String> releaseInventory(@RequestBody Map<String, Object> payload) {
        Long productId = Long.valueOf(payload.get("productId").toString());
        Integer quantity = Integer.valueOf(payload.get("quantity").toString());
        try {
            inventoryService.releaseInventory(productId, quantity);
            return ResponseEntity.ok("Stock released");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Update inventory (restock)
    @PostMapping("/update")
    public ResponseEntity<String> updateInventory(@RequestBody Map<String, Object> payload) {
        Long productId = Long.valueOf(payload.get("productId").toString());
        Integer availableQty = Integer.valueOf(payload.get("availableQty").toString());
        try {
            inventoryService.updateInventory(productId, availableQty);
            return ResponseEntity.ok("Inventory updated");
        } catch (Exception e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Get low stock alerts
    @GetMapping("/alerts")
    public List<Inventory> getLowStockAlerts() {
        int threshold = 5; // Example threshold
        return inventoryService.getLowStockAlerts(threshold);
    }
}
