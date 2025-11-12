package com.MotorSport.LegendaryMotorSport.service;

import org.springframework.stereotype.Service;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.repository.UserRepository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;


@Service
public class UserService {

    private final UserRepository userRepository;

   
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User saveUser(User user) {
        return userRepository.save(user);
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
                user.setPassword(userData.getPassword());
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