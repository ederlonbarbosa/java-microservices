package com.ebc.ead.authuser.controller;

import com.ebc.ead.authuser.dtos.UserDto;
import com.ebc.ead.authuser.model.User;
import com.ebc.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@CrossOrigin(originPatterns = "*", maxAge = 3600)
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
    }

    @GetMapping("/{userId}")
    public ResponseEntity<Object> getOneUser(@PathVariable("userId") UUID userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        return ResponseEntity.status(HttpStatus.OK).body(userOptional.get());
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Object> deleteUser(@PathVariable("userId") UUID userId) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        userService.delete(userOptional.get());
        return ResponseEntity.status(HttpStatus.OK).body("User deleted successfully.");
    }

    @PutMapping("/{userId}")
    public ResponseEntity<Object> updateUser(@PathVariable("userId") UUID userId, @RequestBody @JsonView(UserDto.UserView.UserPut.class) UserDto userDto) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var user = userOptional.get();
        user.setFullname(userDto.getFullname());
        user.setPhoneNumber(userDto.getPhoneNumber());
        user.setCpf(userDto.getCpf());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<Object> updatePassword(@PathVariable("userId") UUID userId, @RequestBody @JsonView(UserDto.UserView.PasswordPut.class) UserDto userDto) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        if (!userOptional.get().getPassword().equals(userDto.getOldPassword())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Mismatched old password!");
        }
        var user = userOptional.get();
        user.setPassword(userDto.getPassword());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body("Password updated successfully");
    }

    @PutMapping("/{userId}/image")
    public ResponseEntity<Object> updateImage(@PathVariable("userId") UUID userId, @RequestBody @JsonView(UserDto.UserView.ImagePut.class) UserDto userDto) {
        Optional<User> userOptional = userService.findById(userId);
        if (userOptional.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found");
        }
        var user = userOptional.get();
        user.setImageUrl(userDto.getImageUrl());
        userService.save(user);
        return ResponseEntity.status(HttpStatus.OK).body(user);
    }
}
