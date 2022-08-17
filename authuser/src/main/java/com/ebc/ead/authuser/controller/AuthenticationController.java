package com.ebc.ead.authuser.controller;

import com.ebc.ead.authuser.dtos.UserDto;
import com.ebc.ead.authuser.model.User;
import com.ebc.ead.authuser.service.UserService;
import com.fasterxml.jackson.annotation.JsonView;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static com.ebc.ead.authuser.model.enums.UserStatus.ACTIVE;
import static com.ebc.ead.authuser.model.enums.UserType.STUDENT;

@Slf4j
@RestController
@CrossOrigin(origins = "*", maxAge = 3600)
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final UserService userService;

    @PostMapping("/signup")
    public ResponseEntity<Object> registerUser(@RequestBody @JsonView(UserDto.UserView.RegistrationPost.class) UserDto userDto) {
        if (userService.existsByUsername(userDto.getUsername())) {
            log.error("Error: Username {} is Already Taken!", userDto.getUsername());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Username is Already Taken!");
        }
        if (userService.existsByEmail(userDto.getEmail())) {
            log.error("Error: Email {} is Already Taken!", userDto.getEmail());
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Error: Email is Already Taken!");
        }
        var user = new User();
        BeanUtils.copyProperties(userDto, user);
        user.setUserStatus(ACTIVE);
        user.setUserType(STUDENT);
        userService.save(user);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }
}
