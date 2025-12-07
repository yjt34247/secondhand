package com.example.secondhand.dao;

import com.example.secondhand.entity.User;

import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface UserDao {
    int insert(User user) throws SQLException;
    Optional<User> findById(int id) throws SQLException;
    Optional<User> findByUsername(String username) throws SQLException;
    List<User> findAll() throws SQLException;
    int update(User user) throws SQLException;
    int delete(int id) throws SQLException;
    boolean existsByUsername(String username) throws SQLException;
}