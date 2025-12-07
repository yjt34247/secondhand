package com.example.secondhand.dao;

import com.example.secondhand.entity.Item;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface ItemDao {
    int insert(Item item) throws SQLException;
    Optional<Item> findById(int id) throws SQLException;
    List<Item> findAll() throws SQLException;
    List<Item> findByUserId(int userId) throws SQLException;
    List<Item> findByKeyword(String keyword) throws SQLException;
    List<Item> findByCategory(String category) throws SQLException;
    List<Item> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException;
    int update(Item item) throws SQLException;
    int delete(int id) throws SQLException;
    int updateStatus(int id, String status) throws SQLException;
}