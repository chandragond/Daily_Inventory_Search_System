package com.Flipkart_Daily.Services;

import com.Flipkart_Daily.Entities.Item;
import com.Flipkart_Daily.Exception.ItemNotFoundException;
import com.Flipkart_Daily.Repositories.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class ItemServiceImpl implements ItemService {

    @Autowired
    private ItemRepository itemRepository;

    @Override
    public void addItem(String brand, String category, int price) {
        Optional<Item> existing = itemRepository.findAll().stream()
                .filter(item -> item.getBrand().equalsIgnoreCase(brand) && item.getCategory().equalsIgnoreCase(category))
                .findFirst();

        if (existing.isEmpty()) {
            itemRepository.save(new Item(brand, category, price));
        }
    }

    @Override
    public void addInventory(String brand, String category, int quantity) {
        Optional<Item> optionalItem = itemRepository.findAll().stream()
                .filter(item -> item.getBrand().equalsIgnoreCase(brand) && item.getCategory().equalsIgnoreCase(category))
                .findFirst();

        if (optionalItem.isPresent()) {
            Item item = optionalItem.get();
            item.addQuantity(quantity);
            itemRepository.save(item);
        } else {
            throw new ItemNotFoundException("Item not found: " + brand + ", " + category);
        }
    }

    @Override
    public List<Item> searchItems(Map<String, List<String>> filters, int[] priceRange, String orderBy, boolean asc) {
        return itemRepository.findAll().stream()
                .filter(item -> {
                    if (filters.containsKey("brand") && !filters.get("brand").contains(item.getBrand())) return false;
                    if (filters.containsKey("category") && !filters.get("category").contains(item.getCategory())) return false;
                    if (priceRange != null) {
                        int from = priceRange[0], to = priceRange[1];
                        if ((from != -1 && item.getPrice() < from) || (to != -1 && item.getPrice() > to)) return false;
                    }
                    return true;
                })
                .sorted((a, b) -> {
                    int cmp = 0;
                    switch (orderBy.toLowerCase()) {
                        case "price": cmp = Integer.compare(a.getPrice(), b.getPrice()); break;
                        case "itemqty": cmp = Integer.compare(a.getQuantity(), b.getQuantity()); break;
                    }
                    return asc ? cmp : -cmp;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }
}
