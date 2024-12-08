package com.example.bookingservice.service;

import com.example.bookingservice.entity.User;
import com.example.bookingservice.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserRepository userRepository;

    public User saveUser(User user) {
        logger.debug("Received request to save user: {}", user);
        User savedUser = userRepository.save(user);
        logger.info("User saved successfully: {}", savedUser);
        return savedUser;
    }

    public User getUserById(Long id) {
        logger.debug("Received request to get user by id: {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            logger.info("Retrieved user: {}", user);
        } else {
            logger.warn("User not found with id: {}", id);
        }
        return user;
    }

    public List<User> getAllUsers() {
        logger.debug("Received request to get all users");
        List<User> users = userRepository.findAll();
        logger.info("Retrieved {} users", users.size());
        return users;
    }

    public User updateUser(Long id, User userDetails) {
        logger.debug("Received request to update user with id: {}", id);
        User user = userRepository.findById(id).orElse(null);
        if (user != null) {
            user.setName(userDetails.getName());
            User updatedUser = userRepository.save(user);
            logger.info("User with id {} updated successfully: {}", id, updatedUser);
            return updatedUser;
        } else {
            logger.warn("User not found with id: {}", id);
            return null;
        }
    }

    public void deleteUser(Long id) {
        logger.debug("Received request to delete user with id: {}", id);
        userRepository.deleteById(id);
        logger.info("User with id {} deleted successfully", id);
    }
}
