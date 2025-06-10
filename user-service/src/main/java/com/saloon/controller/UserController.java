package com.saloon.controller;

import com.saloon.model.User;
import com.saloon.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/api/users")
    public User createUser(@RequestBody User user){
        return userRepository.save(user);
    }

    @GetMapping("/api/users")
    public User getUser(){
        User user = new User();

        user.setEmail("ranul@gmail.com");
        user.setFullName("Ranul Rathnayake");
        user.setContact("+94123456789");
        user.setRole("Customer");
        return user;
    }
}
