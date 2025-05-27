package com.g02tumulak.appdev.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.g02tumulak.appdev.entity.UserEntity;
import com.g02tumulak.appdev.service.UserService;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<UserEntity> getAllUsers() {
        return userService.getAllUsers();
    }

    @GetMapping("/type/{type}")
    public ResponseEntity<List<UserEntity>> getUsersByType(@PathVariable String type) {
        List<UserEntity> users = userService.getUsersByType(type);
        return ResponseEntity.ok(users);
    }

    @GetMapping("/{idNumber}")
    public ResponseEntity<UserEntity> getUserByIdNumber(@PathVariable String idNumber) {
        Optional<UserEntity> user = userService.getUserByIdNumber(idNumber);
        return user.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody UserEntity user) {
        try {
            UserEntity createdUser = userService.createUser(user);
            return ResponseEntity.ok(createdUser);
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @PutMapping("/{idNumber}")
    public ResponseEntity<?> updateUser(@PathVariable String idNumber, @RequestBody UserEntity userDetails) {
        try {
            UserEntity updatedUser = userService.updateUser(idNumber, userDetails);
            return updatedUser != null ? ResponseEntity.ok(updatedUser) : ResponseEntity.notFound().build();
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body(e.getMessage());
        }
    }

    @DeleteMapping("/{idNumber}")
    public ResponseEntity<Void> deleteUser(@PathVariable String idNumber) {
        userService.deleteUser(idNumber);
        return ResponseEntity.ok().build();
    }
}