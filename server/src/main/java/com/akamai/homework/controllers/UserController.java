package com.akamai.homework.controllers;

import com.akamai.homework.dao.entities.User;
import com.akamai.homework.dao.repositories.UserRepository;
import com.akamai.homework.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
public class UserController {
    UserService userService;
    UserRepository userRepository;
    @Autowired
    public UserController(UserService userService,
                          UserRepository userRepository) {
        this.userService =  userService;
        this.userRepository = userRepository;
    }

    @PostMapping("/addUser")
    public String addUser(@RequestParam(name = "userName") String userName, @RequestParam(name = "password") String password) {
        return userService.addUser(userName, password);
    }

    @PostMapping("/updateUser")
    public String updateUser(@RequestParam(name = "id") String id) { //TODO: update users's password
        return userService.updateUser(id);
    }

    @GetMapping("/getAllUsers")
    public List<User> getUserAllUsers() {
        return userService.getAllUsers();
    }
}