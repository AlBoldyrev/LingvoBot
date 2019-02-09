package com.vk.service.impl;

import com.vk.dao.UserDTO;
import com.vk.entities.User;
import com.vk.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDTO usersDao = new UserDTO();

    public UserServiceImpl() {
    }

    public User findUser(int id) {
        return usersDao.findById(id);
    }

    public void saveUser(User user) {
        usersDao.save(user);
    }

    public void deleteUser(User user) {
        usersDao.delete(user);
    }

    public void updateUser(User user) {
        usersDao.update(user);
    }

    public List<User> findAllUsers() {
        return usersDao.findAll();
    }

}
