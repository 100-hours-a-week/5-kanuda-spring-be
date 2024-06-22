package com.ktb.community.controller;

import com.ktb.community.entity.User;
import com.ktb.community.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public ResponseEntity<String> registerUser(@RequestBody User user) {
        try {
            userService.createUser(user);
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/check-email")
    public ResponseEntity<String> checkEmailExists(@RequestParam String email) {
        boolean exists = userService.checkEmail(email);
        if(exists) {
            return new ResponseEntity<>("Email already in use", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Email available", HttpStatus.OK);
        }
    }

    @GetMapping("/check-nickname")
    public ResponseEntity<String> checkNicknameExists(@RequestParam String nickname) {
        boolean exists = userService.checkNickname(nickname);
        if(exists) {
            return new ResponseEntity<>("Nickname already in use", HttpStatus.CONFLICT);
        } else {
            return new ResponseEntity<>("Nickname available", HttpStatus.OK);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getUsersById(@PathVariable Long id) {
        try {
            User user = userService.getUserById(id);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateUser(@PathVariable Long id, @RequestBody User user) {
        try {
            user.setId(id);
            userService.updateUser(user);
            return new ResponseEntity<>("User updated successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUserById(id);
            return new ResponseEntity<>("User deleted successfully", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping
    public ResponseEntity<List<User>> getUserList() {
        List<User> users = userService.getAllUsers();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }
}
