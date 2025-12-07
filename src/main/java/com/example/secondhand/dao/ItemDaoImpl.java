package com.example.secondhand.dao;

import com.example.secondhand.entity.Item;

import java.math.BigDecimal;
import java.sql.*;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ItemDaoImpl implements ItemDao {

    // Item对象的行映射函数
    private final Function<ResultSet, Item> itemMapper = rs -> {
        try {
            Item item = new Item();
            item.setId(rs.getInt("id"));
            item.setUserId(rs.getInt("user_id"));
            item.setTitle(rs.getString("title"));
            item.setDescription(rs.getString("description"));
            item.setPrice(rs.getBigDecimal("price"));
            item.setCategory(rs.getString("category"));
            item.setStatus(rs.getString("status"));

            Timestamp createdAt = rs.getTimestamp("created_at");
            if (createdAt != null) {
                item.setCreatedAt(createdAt.toLocalDateTime());
            }

            Timestamp updatedAt = rs.getTimestamp("updated_at");
            if (updatedAt != null) {
                item.setUpdatedAt(updatedAt.toLocalDateTime());
            }

            try {
                item.setUsername(rs.getString("username"));
            } catch (SQLException e) {
            }

            return item;
        } catch (SQLException e) {
            throw new RuntimeException("映射Item对象失败", e);
        }
    };

    @Override
    public int insert(Item item) throws SQLException {
        String sql = "INSERT INTO items (user_id, title, description, price, category, status) VALUES (?, ?, ?, ?, ?, ?)";
        return JdbcHelper.executeInsertReturnKey(sql,
                item.getUserId(),
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCategory(),
                item.getStatus() != null ? item.getStatus() : "在售"
        );
    }

    @Override
    public Optional<Item> findById(int id) throws SQLException {
        String sql = "SELECT i.*, u.username FROM items i LEFT JOIN users u ON i.user_id = u.id WHERE i.id = ?";
        Item item = JdbcHelper.queryForObject(sql, itemMapper, id);
        return Optional.ofNullable(item);
    }

    @Override
    public List<Item> findAll() throws SQLException {
        String sql = "SELECT i.*, u.username FROM items i LEFT JOIN users u ON i.user_id = u.id WHERE i.status = '在售' ORDER BY i.created_at DESC";
        return JdbcHelper.queryForList(sql, itemMapper);
    }

    @Override
    public List<Item> findByUserId(int userId) throws SQLException {
        String sql = "SELECT i.*, u.username FROM items i LEFT JOIN users u ON i.user_id = u.id WHERE i.user_id = ? ORDER BY i.created_at DESC";
        return JdbcHelper.queryForList(sql, itemMapper, userId);
    }

    @Override
    public List<Item> findByKeyword(String keyword) throws SQLException {
        String sql = "SELECT i.*, u.username FROM items i LEFT JOIN users u ON i.user_id = u.id " +
                "WHERE i.status = '在售' AND (i.title LIKE ? OR i.description LIKE ? OR i.category LIKE ?) " +
                "ORDER BY i.created_at DESC";
        String searchPattern = "%" + keyword + "%";
        return JdbcHelper.queryForList(sql, itemMapper, searchPattern, searchPattern, searchPattern);
    }

    @Override
    public List<Item> findByCategory(String category) throws SQLException {
        String sql = "SELECT i.*, u.username FROM items i LEFT JOIN users u ON i.user_id = u.id " +
                "WHERE i.status = '在售' AND i.category = ? ORDER BY i.created_at DESC";
        return JdbcHelper.queryForList(sql, itemMapper, category);
    }

    @Override
    public List<Item> findByPriceRange(BigDecimal minPrice, BigDecimal maxPrice) throws SQLException {
        String sql = "SELECT i.*, u.username FROM items i LEFT JOIN users u ON i.user_id = u.id " +
                "WHERE i.status = '在售' AND i.price BETWEEN ? AND ? ORDER BY i.price";
        return JdbcHelper.queryForList(sql, itemMapper, minPrice, maxPrice);
    }

    @Override
    public int update(Item item) throws SQLException {
        String sql = "UPDATE items SET title = ?, description = ?, price = ?, category = ?, status = ? WHERE id = ?";
        return JdbcHelper.executeUpdate(sql,
                item.getTitle(),
                item.getDescription(),
                item.getPrice(),
                item.getCategory(),
                item.getStatus(),
                item.getId()
        );
    }

    @Override
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM items WHERE id = ?";
        return JdbcHelper.executeUpdate(sql, id);
    }

    @Override
    public int updateStatus(int id, String status) throws SQLException {
        String sql = "UPDATE items SET status = ? WHERE id = ?";
        return JdbcHelper.executeUpdate(sql, status, id);
    }
}