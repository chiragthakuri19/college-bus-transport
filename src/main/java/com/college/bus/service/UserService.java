package com.college.bus.service;

import com.college.bus.model.User;
import com.college.bus.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public User createUser(User user) {
        if (userRepository.existsByUsername(user.getUsername())) {
            throw new RuntimeException("Username is already taken");
        }
        if (userRepository.existsByEmail(user.getEmail())) {
            throw new RuntimeException("Email is already registered");
        }

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }

    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    public Optional<User> getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public List<User> getAllStudents() {
        return userRepository.findByRole(User.UserRole.STUDENT);
    }

    public User updateUser(User user) {
        return userRepository.save(user);
    }

    public void updateTransportFeePaid(Long userId, boolean paid) {
        userRepository.findById(userId).ifPresent(user -> {
            user.setTransportFeePaid(paid);
            userRepository.save(user);
        });
    }

    public boolean isTransportFeePaid(Long userId) {
        return userRepository.findById(userId)
                .map(User::isTransportFeePaid)
                .orElse(false);
    }
} 