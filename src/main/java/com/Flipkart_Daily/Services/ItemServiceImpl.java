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
    public void addItem(Item item) {
        Optional<Item> existing = itemRepository.findAll().stream()
                .filter(i -> i.getBrand().equalsIgnoreCase(item.getBrand())
                        && i.getCategory().equalsIgnoreCase(item.getCategory()))
                .findFirst();

        if (existing.isEmpty()) {
            itemRepository.save(item);
        }
    }

    @Override
    public void addItem(String brand, String category, int price) {
        Optional<Item> existing = itemRepository.findAll().stream()
                .filter(i -> i.getBrand().equalsIgnoreCase(brand)
                        && i.getCategory().equalsIgnoreCase(category))
                .findFirst();

        if (existing.isEmpty()) {
            itemRepository.save(new Item(brand, category, price, 0));
        }
    }

    @Override
    public void addInventory(String brand, String category, int quantity) {
        Optional<Item> optionalItem = itemRepository.findAll().stream()
                .filter(i -> i.getBrand().equalsIgnoreCase(brand)
                        && i.getCategory().equalsIgnoreCase(category))
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
        return List.of();
    }

    @Override
    public List<Item> searchItems(List<String> brands, List<String> categories, Integer minPrice, Integer maxPrice, String orderBy, boolean asc) {
        int from = (minPrice != null) ? minPrice : -1;
        int to = (maxPrice != null) ? maxPrice : -1;

        return itemRepository.findAll().stream()
                .filter(item -> (brands == null || brands.contains(item.getBrand())))
                .filter(item -> (categories == null || categories.contains(item.getCategory())))
                .filter(item -> (from == -1 || item.getPrice() >= from))
                .filter(item -> (to == -1 || item.getPrice() <= to))
                .sorted((a, b) -> {
                    int cmp = 0;
                    switch (orderBy.toLowerCase()) {
                        case "price": cmp = Integer.compare(a.getPrice(), b.getPrice()); break;
                        case "quantity": cmp = Integer.compare(a.getQuantity(), b.getQuantity()); break;
                    }
                    return asc ? cmp : -cmp;
                })
                .collect(Collectors.toList());
    }

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public void updateItem(String brand, String category, Integer price, Integer quantity) {
    }
}
