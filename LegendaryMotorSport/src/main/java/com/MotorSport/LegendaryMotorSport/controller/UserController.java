package com.MotorSport.LegendaryMotorSport.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.service.UserService;

import java.util.Optional;


@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    
    public UserController(UserService userService) {
        this.userService = userService;
    }

    //  Registro de usuario
    @PostMapping("/register")
    ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    // Login (simulado)
    @PostMapping("/login")
    public ResponseEntity<String> login(@RequestBody User loginRequest) {
        Optional<User> userOpt = userService.findByUsername(loginRequest.getUsername());

        if (userOpt.isPresent()) {
            User user = userOpt.get();
            if (user.getPassword().equals(loginRequest.getPassword())) {
                return ResponseEntity.ok("Login exitoso");
            } else {
                return ResponseEntity.status(401).body("Contraseña incorrecta");
            }
        } else {
            return ResponseEntity.status(404).body("Usuario no encontrado");
        }
    }

    // Buscar usuario por ID
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar usuario por username
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Buscar usuario por email
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}