package com.vk.service.impl;

import com.vk.dao.UserDTO;
import com.vk.entities.User;
import com.vk.service.UserService;

import java.util.List;

public class UserServiceImpl implements UserService {

    private UserDTO userDTO = new UserDTO();

    public UserServiceImpl() {
    }

    public User findUser(int id) {
        return userDTO.findById(id);
    }

    public void saveUser(User user) {
        userDTO.save(user);
    }

    public void deleteUser(User user) {
        userDTO.delete(user);
    }

    public void updateUser(User user) {
        userDTO.update(user);
    }

    public List<User> findAllUsers() {
        return userDTO.findAll();
    }

}
