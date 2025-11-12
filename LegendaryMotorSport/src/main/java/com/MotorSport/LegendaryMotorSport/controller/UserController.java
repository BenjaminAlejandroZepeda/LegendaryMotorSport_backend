package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Tag(name = "Usuarios", description = "Operaciones relacionadas con el registro, autenticación y consulta de usuarios")
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }


    @Operation(summary = "Obtiene todos los usuarios registrados")
    @ApiResponse(responseCode = "200", description = "Lista de usuarios obtenida correctamente")
    @GetMapping
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = userService.findAllUsers();
        return ResponseEntity.ok(users);
    }

    @Operation(summary = "Registra un nuevo usuario")
    @ApiResponse(responseCode = "200", description = "Usuario registrado exitosamente")
    @PostMapping("/register")
    public ResponseEntity<User> registerUser(@RequestBody User user) {
        User savedUser = userService.saveUser(user);
        return ResponseEntity.ok(savedUser);
    }

    @Operation(summary = "Inicia sesión con nombre de usuario y contraseña")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Login exitoso"),
        @ApiResponse(responseCode = "401", description = "Contraseña incorrecta"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
        @PostMapping("/login")
        public ResponseEntity<User> login(@RequestBody User loginRequest) {
            Optional<User> userOpt = userService.findByEmail(loginRequest.getEmail());

            if (userOpt.isPresent()) {
                User user = userOpt.get();
                if (user.getPassword().equals(loginRequest.getPassword())) {
                    
                    user.setLastLogin(LocalDateTime.now());
                    userService.saveUser(user);

                    
                    return ResponseEntity.ok(user);
                } else {
                    return ResponseEntity.status(401).body(null); 
                }
            } else {
                return ResponseEntity.status(404).body(null); 
            }
        }

    @Operation(summary = "Obtiene un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return userService.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtiene un usuario por su nombre de usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/username/{username}")
    public ResponseEntity<User> getUserByUsername(@PathVariable String username) {
        return userService.findByUsername(username)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtiene un usuario por su correo electrónico")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario encontrado"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @GetMapping("/email/{email}")
    public ResponseEntity<User> getUserByEmail(@PathVariable String email) {
        return userService.findByEmail(email)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Actualiza los datos de un usuario existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario actualizado exitosamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User updatedUser) {
        Optional<User> existingUserOpt = userService.findById(id);

        if (existingUserOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        User existingUser = existingUserOpt.get();
        
        existingUser.setUsername(updatedUser.getUsername());
        existingUser.setEmail(updatedUser.getEmail());
        existingUser.setPassword(updatedUser.getPassword());
        existingUser.setRole(updatedUser.getRole());

        existingUser.setLastLogin(LocalDateTime.now());

        User savedUser = userService.saveUser(existingUser);
        return ResponseEntity.ok(savedUser);
    }


    @Operation(summary = "Elimina un usuario por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Usuario eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Usuario no encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        Optional<User> userOpt = userService.findById(id);
        if (userOpt.isPresent()) {
            userService.deleteUser(id);
            return ResponseEntity.ok().build();
        } else {
            return ResponseEntity.notFound().build();
        }
    }

}
