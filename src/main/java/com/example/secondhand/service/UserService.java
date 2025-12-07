package com.example.secondhand.service;

import com.example.secondhand.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    int register(User user) throws Exception;
    Optional<User> login(String username, String password) throws Exception;
    Optional<User> getUserById(int id) throws Exception;
    List<User> getAllUsers() throws Exception;
    boolean updateUser(User user) throws Exception;
    boolean deleteUser(int id) throws Exception;
    boolean isUsernameExists(String username) throws Exception;
}