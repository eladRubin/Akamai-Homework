package com.akamai.homework.services;

import com.akamai.homework.dao.entities.User;
import com.akamai.homework.dao.repositories.UserRepository;
import com.akamai.homework.security.payload.request.SignupRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class UserService {
    UserRepository userRepository;
    @Autowired
    PasswordEncoder encoder;
    @Autowired
    public UserService(UserRepository userRepository, PasswordEncoder encoder) {
        this.userRepository = userRepository;
        this.encoder = encoder;
    }

    public String registerUser(SignupRequest signUpRequest) throws Exception {
        if (userRepository.existsByUserName(signUpRequest.getUsername())) {
            throw new Exception("Error: Username is already taken!");
        }

        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            throw new Exception("Error: Email is already in use!");
        }

        User user = new User(signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()));

        userRepository.saveAndFlush(user);

        return "User registered successfully!";
    }
}
