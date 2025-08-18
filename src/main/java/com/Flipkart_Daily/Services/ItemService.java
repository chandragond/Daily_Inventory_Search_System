package com.Flipkart_Daily.Services;

import com.Flipkart_Daily.Entities.Item;

import java.util.List;
import java.util.Map;

public interface ItemService {
    void addItem(String brand, String category, int price);
    void addInventory(String brand, String category, int quantity);
    List<Item> searchItems(Map<String, List<String>> filters, int[] priceRange, String orderBy, boolean asc);
    List<Item> getAllItems();
}
