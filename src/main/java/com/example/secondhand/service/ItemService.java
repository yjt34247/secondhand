package com.example.secondhand.service;

import com.example.secondhand.entity.Item;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface ItemService {
    int publishItem(Item item) throws Exception;
    Optional<Item> getItemById(int id) throws Exception;
    List<Item> getAllItems() throws Exception;
    List<Item> getItemsByUserId(int userId) throws Exception;
    List<Item> searchItems(String keyword) throws Exception; // 模糊搜索
    List<Item> getItemsByCategory(String category) throws Exception;
    List<Item> getItemsByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws Exception;
    boolean updateItem(Item item) throws Exception;
    boolean deleteItem(int id) throws Exception;
    boolean updateItemStatus(int id, String status) throws Exception;
}