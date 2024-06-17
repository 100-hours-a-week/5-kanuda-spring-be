package com.ktb.community.service;

import com.ktb.community.entity.User;
import com.ktb.community.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public boolean checkEmail(String email) {
        return userRepository.existsByEmail(email);
    }

    public boolean checkNickname(String nickname) {
        return userRepository.existsByNickname(nickname);
    }

    public User getUserById(Long id) {
        return userRepository.findById(id);
    }

    public void createUser(User user) {
        if(!checkEmail(user.getEmail())) {
            throw new RuntimeException("Email already in use");
        }
        userRepository.save(user);
    }

    public void updateUser(User user) {
        if(!userRepository.existsById(user.getId())) {
            throw new RuntimeException("User not exist");
        }
        userRepository.save(user);
    }

    public void deleteUserById(Long id) {
        if(!userRepository.existsById(id)) {
            throw new RuntimeException("User not exist");
        }
        userRepository.deleteById(id);
    }
}
