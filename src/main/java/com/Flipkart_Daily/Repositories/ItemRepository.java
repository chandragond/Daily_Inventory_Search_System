package com.Flipkart_Daily.Repositories;

import com.Flipkart_Daily.Entities.Item;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Long> {
    List<Item> findByBrandIn(List<String> brands);
    List<Item> findByCategoryIn(List<String> categories);
}
