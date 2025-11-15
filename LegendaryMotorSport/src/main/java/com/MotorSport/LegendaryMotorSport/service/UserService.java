package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.repository.UserRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }


    public User saveUser(User user) {
        return userRepository.save(user);
    }

 
    public User registerUser(User user) {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userRepository.save(user);
    }


    public boolean checkPassword(User user, String rawPassword) {
        return passwordEncoder.matches(rawPassword, user.getPassword());
    }

    public Optional<User> findByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    public Optional<User> findByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    public List<User> findAllUsers() {
        return userRepository.findAll();
    }


    public User updateUser(Long id, User userData) {
        return userRepository.findById(id)
            .map(user -> {
                user.setUsername(userData.getUsername());
                user.setEmail(userData.getEmail());
          
                user.setPassword(passwordEncoder.encode(userData.getPassword()));
                user.setRole(userData.getRole());
                user.setLastLogin(LocalDateTime.now());
                return userRepository.save(user);
            })
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
    }

  
    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }
}
