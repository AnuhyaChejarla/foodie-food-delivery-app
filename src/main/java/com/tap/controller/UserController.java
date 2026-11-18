package com.tap.controller;

import java.util.List;
import java.util.Map;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.tap.dto.UserDTO;
import com.tap.dto.UserRequestDTO;
import com.tap.service.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }
    
    @PostMapping("/login")
    public UserDTO login(@RequestBody Map<String, String> loginData) {

        String email = loginData.get("email");
        String password = loginData.get("password");

        return userService.login(email, password);
    }

    @GetMapping
    public List<UserDTO> getUsers() {
        return userService.getUsers();
    }

    @GetMapping("/{userId}")
    public UserDTO getUserById(@PathVariable int userId) {
        return userService.getUserById(userId);
    }

    @PostMapping
    public UserDTO addUser(@Valid @RequestBody UserRequestDTO userDTO) {
        return userService.addUser(userDTO);
    }
    
    @PutMapping("/{userId}")
    public UserDTO updateUser(@PathVariable int userId,
    		@Valid @RequestBody UserRequestDTO userDTO) {
        return userService.updateUser(userId, userDTO);
    }

    @DeleteMapping("/{userId}")
    public String deleteUser(@PathVariable int userId) {
        userService.deleteUser(userId);
        return "User deleted successfully";
    }
}