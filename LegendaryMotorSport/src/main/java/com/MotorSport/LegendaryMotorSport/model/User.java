package com.MotorSport.LegendaryMotorSport.model;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidad que representa a un usuario registrado en el sistema")
@Entity
@Table(name = "users")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

    @Schema(description = "ID único del usuario", example = "1")
    @Id
    @SequenceGenerator(name = "user_seq", sequenceName = "user_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq")
    private Long id;

    @Schema(description = "Nombre de usuario único", example = "benja123")
    @Column(nullable = false, unique = true)
    private String username;

    @Schema(description = "Correo electrónico del usuario", example = "benja@example.com")
    @Column(nullable = false, unique = true)
    private String email;

    @Schema(description = "Contraseña del usuario (en texto plano o cifrada)", example = "1234")
    @Column(nullable = false)
    private String password;

    @Schema(description = "Rol asignado al usuario (por ejemplo: ADMIN, CLIENTE)", example = "CLIENTE")
    @Column(nullable = false)
    private String role;

    @Schema(description = "Fecha y hora del último inicio de sesión", example = "2025-11-10T01:00:00")
    private LocalDateTime lastLogin;
}
