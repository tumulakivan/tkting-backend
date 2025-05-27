package com.g02tumulak.appdev.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.g02tumulak.appdev.entity.UserEntity;
import com.g02tumulak.appdev.repository.UserRepository;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<UserEntity> getAllUsers() {
        return userRepository.findAll();
    }

    public List<UserEntity> getUsersByType(String type) {
        return userRepository.findByType(type);
    }

    public Optional<UserEntity> getUserByIdNumber(String idNumber) {
        UserEntity user = userRepository.findByIdNumber(idNumber);
        return Optional.ofNullable(user);
    }

    public UserEntity createUser(UserEntity user) {
        if (userRepository.findByIdNumber(user.getIdNumber()) != null) {
            throw new RuntimeException("A user with this ID Number already exists");
        }
        if (!isValidUserType(user.getType())) {
            throw new RuntimeException("Invalid user type. Must be either 'student' or 'staff'");
        }
        return userRepository.save(user);
    }

    public UserEntity updateUser(String idNumber, UserEntity userDetails) {
        UserEntity existingUser = userRepository.findByIdNumber(idNumber);
        if (existingUser != null) {
            existingUser.setFirstName(userDetails.getFirstName());
            existingUser.setLastName(userDetails.getLastName());
            existingUser.setPassword(userDetails.getPassword());
            if (userDetails.getType() != null && isValidUserType(userDetails.getType())) {
                existingUser.setType(userDetails.getType());
            }
            return userRepository.save(existingUser);
        }
        return null;
    }

    public void deleteUser(String idNumber) {
        UserEntity user = userRepository.findByIdNumber(idNumber);
        if (user != null) {
            userRepository.delete(user);
        }
    }

    private boolean isValidUserType(String type) {
        return type != null && (type.equalsIgnoreCase("student") || type.equalsIgnoreCase("staff"));
    }
}