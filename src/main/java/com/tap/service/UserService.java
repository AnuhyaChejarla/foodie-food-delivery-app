
package com.tap.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.tap.dto.UserDTO;
import com.tap.dto.UserRequestDTO;
import com.tap.model.User;
import com.tap.repository.UserRepo;

@Service
public class UserService {

    private final UserRepo userRepo;
    private final BCryptPasswordEncoder passwordEncoder;

    public UserService(UserRepo userRepo) {
        this.userRepo = userRepo;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    public List<UserDTO> getUsers() {

        return userRepo.findAll()
                .stream()
                .map(user -> new UserDTO(
                        user.getUserId(),
                        user.getName(),
                        user.getEmail(),
                        user.getPhone(),
                        user.getAddress(),
                        user.getRole()
                ))
                .toList();
    }

    public UserDTO getUserById(int userId) {
        User user = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        return convertToDTO(user);
    }

    public UserDTO addUser(UserRequestDTO userDTO) {

        if (userRepo.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );
        }

        User user = new User();

        user.setName(userDTO.getName());
        user.setEmail(userDTO.getEmail());
        user.setPhone(userDTO.getPhone());
        user.setPassword(passwordEncoder.encode(userDTO.getPassword()));
        user.setAddress(userDTO.getAddress());
        user.setRole("CUSTOMER");

        User savedUser = userRepo.save(user);
        return convertToDTO(savedUser);
    }

    public UserDTO updateUser(int userId, UserRequestDTO userDTO) {

        User existingUser = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "User not found with id: " + userId
                ));

        if (!existingUser.getEmail().equals(userDTO.getEmail())
                && userRepo.findByEmail(userDTO.getEmail()).isPresent()) {
            throw new ResponseStatusException(
                    HttpStatus.CONFLICT,
                    "Email is already registered"
            );
        }

        existingUser.setName(userDTO.getName());
        existingUser.setEmail(userDTO.getEmail());
        existingUser.setPhone(userDTO.getPhone());
        existingUser.setPassword(
                passwordEncoder.encode(userDTO.getPassword())
        );
        existingUser.setAddress(userDTO.getAddress());

        User updatedUser = userRepo.save(existingUser);

        return convertToDTO(updatedUser);
    }

    public void deleteUser(int userId) {

        if (!userRepo.existsById(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND,
                    "User not found with id: " + userId
            );
        }

        userRepo.deleteById(userId);
    }

    public UserDTO login(String email, String password) {

        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.UNAUTHORIZED,
                        "Invalid email or password"
                ));

        if (!passwordEncoder.matches(password, user.getPassword())) {
            throw new ResponseStatusException(
                    HttpStatus.UNAUTHORIZED,
                    "Invalid email or password"
            );
        }

        return convertToDTO(user);
    }

    private UserDTO convertToDTO(User user) {
        return new UserDTO(
                user.getUserId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getAddress(),
                user.getRole()
        );
    }
}
