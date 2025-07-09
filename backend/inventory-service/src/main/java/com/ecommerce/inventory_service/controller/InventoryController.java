package com.ecommerce.inventory_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecommerce.inventory_service.dto.InventoryDTO;
import com.ecommerce.inventory_service.service.InventoryService;

@RestController
@RequestMapping("/api/inventories")
public class InventoryController {

    @Autowired
    private InventoryService service;

    @GetMapping
    public List<InventoryDTO> getAll() {
        return service.getAllInventories();
    }

    @GetMapping("/product/{productId}")
    public InventoryDTO getByProductId(@PathVariable Long productId) {
        return service.getInventoryByProductId(productId);
    }

    @PostMapping
    public InventoryDTO create(@RequestBody InventoryDTO dto) {
        return service.createInventory(dto);
    }

    @PutMapping("/{id}")
    public InventoryDTO update(@PathVariable Long id, @RequestBody InventoryDTO dto) {
        return service.updateInventory(id, dto);
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable Long id) {
        service.deleteInventory(id);
    }
}
