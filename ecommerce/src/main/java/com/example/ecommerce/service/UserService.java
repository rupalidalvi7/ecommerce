package com.example.ecommerce.service;

import com.example.ecommerce.model.User;

import java.util.List;

public interface UserService {

    User getUser(Long id);

    List<User> getAllUsers();

    void deleteUser(Long id);

}
