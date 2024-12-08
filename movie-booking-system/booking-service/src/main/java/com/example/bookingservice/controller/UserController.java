package com.example.bookingservice.controller;

import com.example.bookingservice.entity.User;
import com.example.bookingservice.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    private UserService userService;

    @PostMapping
    public User createUser(@RequestBody User user) {
        logger.debug("Received request to create user: {}", user);
        User createdUser = userService.saveUser(user);
        logger.info("User created successfully: {}", createdUser);
        return createdUser;
    }

    @GetMapping("/{id}")
    public User getUserById(@PathVariable Long id) {
        logger.debug("Received request to get user by id: {}", id);
        User user = userService.getUserById(id);
        if (user != null) {
            logger.info("Retrieved user: {}", user);
        } else {
            logger.warn("User not found with id: {}", id);
        }
        return user;
    }

    @GetMapping
    public List<User> getAllUsers() {
        logger.debug("Received request to get all users");
        List<User> users = userService.getAllUsers();
        logger.info("Retrieved {} users", users.size());
        return users;
    }

    @PutMapping("/{id}")
    public User updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        logger.debug("Received request to update user with id: {}", id);
        User updatedUser = userService.updateUser(id, userDetails);
        if (updatedUser != null) {
            logger.info("User with id {} updated successfully: {}", id, updatedUser);
        } else {
            logger.warn("User not found with id: {}", id);
        }
        return updatedUser;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Long id) {
        logger.debug("Received request to delete user with id: {}", id);
        userService.deleteUser(id);
        logger.info("User with id {} deleted successfully", id);
    }
}
