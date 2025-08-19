package com.Flipkart_Daily.Controllers;

import com.Flipkart_Daily.Entities.Item;
import com.Flipkart_Daily.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/items")
public class ItemController {

    @Autowired
    private ItemService itemService;

    @PostMapping("/add")
    public String addItem(@RequestBody Item item) {
        itemService.addItem(item);
        return "Item added.";
    }

    @PostMapping("/add-multiple")
    public String addMultipleItems(@RequestBody List<Item> items) {
        for (Item item : items) {
            itemService.addItem(item);
        }
        return "Multiple items added.";
    }

    @PostMapping("/add-inventory")
    public String addInventory(@RequestBody Item item) {
        itemService.addInventory(item.getBrand(), item.getCategory(), item.getQuantity());
        return "Inventory added.";
    }

    @GetMapping("/all")
    public List<Item> getAllItems() {
        return itemService.getAllItems();
    }

    @GetMapping("/search")
    public List<Item> searchItems(
            @RequestParam(required = false) List<String> brand,
            @RequestParam(required = false) List<String> category,
            @RequestParam(required = false) Integer minPrice,
            @RequestParam(required = false) Integer maxPrice,
            @RequestParam(defaultValue = "price") String orderBy,
            @RequestParam(defaultValue = "true") boolean asc
    ) {
        return itemService.searchItems(brand, category, minPrice, maxPrice, orderBy, asc);
    }
    @PutMapping("/update")
    public String updateItem(@RequestBody Map<String, Object> request) {
        String brand = (String) request.get("brand");
        String category = (String) request.get("category");
        Integer price = (Integer) request.get("price");
        Integer quantity = (Integer) request.get("quantity");

        itemService.updateItem(brand, category, price, quantity);
        return "Item updated successfully.";
    }

}
