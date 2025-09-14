package com.example.wmsapi;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

@RestController
@RequestMapping("/api/items")
@CrossOrigin(origins = "*") // <-- TAMBAHKAN ANOTASI INI
public class WarehouseController {

    private final WarehouseService warehouseService;

    public WarehouseController(WarehouseService warehouseService) {
        this.warehouseService = warehouseService;
    }

    @PostMapping
    public ResponseEntity<Item> createItem(@RequestBody Item item) {
        if (item.getId() == null || item.getId().isEmpty()) {
            return ResponseEntity.badRequest().build();
        }
        Item newItem = warehouseService.addItem(item);
        if (newItem == null) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(newItem);
    }

    @GetMapping
    public Collection<Item> getAllItems() {
        return warehouseService.getAllItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable String id) {
        Item item = warehouseService.findItemById(id);
        if (item == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(item);
    }

    @PutMapping("/{id}/quantity")
    public ResponseEntity<Item> updateQuantity(@PathVariable String id, @RequestBody Map<String, Integer> payload) {
        Integer quantity = payload.get("quantity");
        if (quantity == null) {
            return ResponseEntity.badRequest().build();
        }
        Item updatedItem = warehouseService.updateItemQuantity(id, quantity);
        if (updatedItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable String id) {
        Item removedItem = warehouseService.removeItem(id);
        if (removedItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}

