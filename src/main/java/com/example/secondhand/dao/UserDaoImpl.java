package com.example.secondhand.dao;

import com.example.secondhand.entity.User;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class UserDaoImpl implements UserDao {

    // User对象的行映射函数
    private final Function<ResultSet, User> userMapper = rs -> {
        try {
            User user = new User();
            user.setId(rs.getInt("id"));
            user.setUsername(rs.getString("username"));
            user.setPassword(rs.getString("password"));
            user.setEmail(rs.getString("email"));
            user.setPhone(rs.getString("phone"));

            Timestamp timestamp = rs.getTimestamp("created_at");
            if (timestamp != null) {
                user.setCreatedAt(timestamp.toLocalDateTime());
            }
            return user;
        } catch (SQLException e) {
            throw new RuntimeException("映射User对象失败", e);
        }
    };

    @Override
    public int insert(User user) throws SQLException {
        String sql = "INSERT INTO users (username, password, email, phone) VALUES (?, ?, ?, ?)";
        return JdbcHelper.executeInsertReturnKey(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone()
        );
    }

    @Override
    public Optional<User> findById(int id) throws SQLException {
        String sql = "SELECT * FROM users WHERE id = ?";
        User user = JdbcHelper.queryForObject(sql, userMapper, id);
        return Optional.ofNullable(user);
    }

    @Override
    public Optional<User> findByUsername(String username) throws SQLException {
        String sql = "SELECT * FROM users WHERE username = ?";
        User user = JdbcHelper.queryForObject(sql, userMapper, username);
        return Optional.ofNullable(user);
    }

    @Override
    public List<User> findAll() throws SQLException {
        String sql = "SELECT * FROM users ORDER BY created_at DESC";
        return JdbcHelper.queryForList(sql, userMapper);
    }

    @Override
    public int update(User user) throws SQLException {
        String sql = "UPDATE users SET username = ?, password = ?, email = ?, phone = ? WHERE id = ?";
        return JdbcHelper.executeUpdate(sql,
                user.getUsername(),
                user.getPassword(),
                user.getEmail(),
                user.getPhone(),
                user.getId()
        );
    }

    @Override
    public int delete(int id) throws SQLException {
        String sql = "DELETE FROM users WHERE id = ?";
        return JdbcHelper.executeUpdate(sql, id);
    }

    @Override
    public boolean existsByUsername(String username) throws SQLException {
        String sql = "SELECT COUNT(*) as count FROM users WHERE username = ?";

        // 使用匿名函数处理计数查询
        Integer count = JdbcHelper.queryForObject(sql, rs -> {
            try {
                return rs.getInt("count");
            } catch (SQLException e) {
                throw new RuntimeException("获取计数失败", e);
            }
        }, username);

        return count != null && count > 0;
    }
}