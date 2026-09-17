package com.example.ecommerce.serviceImp;

import com.example.ecommerce.exception.ResourceNotFoundException;
import com.example.ecommerce.model.User;
import com.example.ecommerce.repo.UserRepo;
import com.example.ecommerce.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImp implements UserService {

    private final UserRepo userRepo;

    @Override
    public User getUser(Long id) {

        return userRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with Id : " + id));
    }

    @Override
    public List<User> getAllUsers() {

        return userRepo.findAll();
    }

    @Override
    public void deleteUser(Long id) {

        User user = userRepo.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "User not found with Id : " + id));

        userRepo.delete(user);
    }

}