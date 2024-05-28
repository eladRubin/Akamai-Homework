package com.akamai.homework.services;

import com.akamai.homework.dao.entities.User;
import com.akamai.homework.dao.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
public class UserService {
    UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public String addUser (String userName, String password) {
        log.info("adding user to the system: {}", userName);
        User newUser = new User(userName, password);
        userRepository.saveAndFlush(newUser);
        return "saved"; // TODO: implement logic
    }

    public String updateUser (String id) {
        log.info("updateUser with id: {}", id);
        User newUser = new User("elad", "12345"); //TODO: change to userName from Request
        userRepository.saveAndFlush(newUser);
        return "Updated";
    }

    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
