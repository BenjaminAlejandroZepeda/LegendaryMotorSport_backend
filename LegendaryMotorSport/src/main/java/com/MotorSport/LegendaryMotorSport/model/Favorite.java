package com.MotorSport.LegendaryMotorSport.model;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidad que representa un vehículo marcado como favorito por un usuario")
@Entity
@Table(name = "favorites")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Favorite {

    @Schema(description = "ID único del registro de favorito", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Usuario que marcó el vehículo como favorito")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Schema(description = "Vehículo marcado como favorito")
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Schema(description = "Fecha en que se guardó el vehículo como favorito", example = "2025-11-10T01:00:00")
    @Column(nullable = false)
    private LocalDateTime fechaGuardado;
}
