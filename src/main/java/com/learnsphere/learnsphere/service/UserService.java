package com.learnsphere.learnsphere.service;

import com.learnsphere.learnsphere.entity.User;
import com.learnsphere.learnsphere.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    // Register new user
    public User registerUser(User user) {

        // check duplicate email
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        // save user
        return userRepository.save(user);
    }

    // Find user by email (used for login / security later)
    public User getUserByEmail(String email, String password) {
        return userRepository.findByEmailAndPassword(email, password)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}
