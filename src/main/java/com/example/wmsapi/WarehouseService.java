package com.example.wmsapi;

import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class WarehouseService {
    private final Map<String, Item> inventory = new ConcurrentHashMap<>();

    public Item addItem(Item item) {
        if (inventory.containsKey(item.getId())) {
            return null; // Menandakan item sudah ada
        }
        inventory.put(item.getId(), item);
        return item;
    }

    public Collection<Item> getAllItems() {
        return inventory.values();
    }

    public Item findItemById(String id) {
        return inventory.get(id);
    }

    public Item updateItemQuantity(String id, int quantity) {
        Item item = findItemById(id);
        if (item != null) {
            item.setQuantity(quantity);
            return item;
        }
        return null; // Item tidak ditemukan
    }

    public Item removeItem(String id) {
        return inventory.remove(id);
    }
}
