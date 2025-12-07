package com.example.secondhand.service;

import com.example.secondhand.dao.UserDao;
import com.example.secondhand.dao.UserDaoImpl;
import com.example.secondhand.entity.User;
import org.mindrot.jbcrypt.BCrypt;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private final UserDao userDao;

    public UserServiceImpl() {
        this.userDao = new UserDaoImpl();
    }

    @Override
    public int register(User user) throws Exception {
        // 检查用户名是否已存在
        if (userDao.existsByUsername(user.getUsername())) {
            throw new Exception("用户名已存在");
        }

        // 密码加密存储
        String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
        user.setPassword(hashedPassword);

        return userDao.insert(user);
    }

    @Override
    public Optional<User> login(String username, String password) throws Exception {
        Optional<User> userOptional = userDao.findByUsername(username);

        if (userOptional.isPresent()) {
            User user = userOptional.get();
            // 验证密码（BCrypt加密验证）
            if (BCrypt.checkpw(password, user.getPassword())) {
                return Optional.of(user);
            }
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> getUserById(int id) throws Exception {
        return userDao.findById(id);
    }

    @Override
    public List<User> getAllUsers() throws Exception {
        return userDao.findAll();
    }

    @Override
    public boolean updateUser(User user) throws Exception {
        // 如果要更新密码，需要重新加密
        if (user.getPassword() != null && !user.getPassword().isEmpty()) {
            String hashedPassword = BCrypt.hashpw(user.getPassword(), BCrypt.gensalt());
            user.setPassword(hashedPassword);
        }
        return userDao.update(user) > 0;
    }

    @Override
    public boolean deleteUser(int id) throws Exception {
        return userDao.delete(id) > 0;
    }

    @Override
    public boolean isUsernameExists(String username) throws Exception {
        return userDao.existsByUsername(username);
    }
}