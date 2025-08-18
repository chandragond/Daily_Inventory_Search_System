package com.Flipkart_Daily.Controllers;

import com.Flipkart_Daily.Entities.Item;
import com.Flipkart_Daily.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public String addItem(@RequestParam String brand, @RequestParam String category, @RequestParam int price) {
        itemService.addItem(brand, category, price);
        return "Item added.";
    }

    @PostMapping("/add-inventory")
    public String addInventory(@RequestParam String brand, @RequestParam String category, @RequestParam int quantity) {
        itemService.addInventory(brand, category, quantity);
        return "Inventory added.";
    }

    @GetMapping("/search")
    public List<Item> searchItems(@RequestParam(required = false) List<String> brand,
                                  @RequestParam(required = false) List<String> category,
                                  @RequestParam(required = false) Integer from,
                                  @RequestParam(required = false) Integer to,
                                  @RequestParam(defaultValue = "price") String orderBy,
                                  @RequestParam(defaultValue = "true") boolean asc) {
        Map<String, List<String>> filters = new HashMap<>();
        if (brand != null) filters.put("brand", brand);
        if (category != null) filters.put("category", category);

        int[] priceRange = (from != null || to != null) ? new int[]{from != null ? from : -1, to != null ? to : -1} : null;

        return itemService.searchItems(filters, priceRange, orderBy, asc);
    }

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }
}
