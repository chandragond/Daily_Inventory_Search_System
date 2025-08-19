package com.Flipkart_Daily.Controllers;

import com.Flipkart_Daily.Entities.Item;
import com.Flipkart_Daily.Services.ItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
}
