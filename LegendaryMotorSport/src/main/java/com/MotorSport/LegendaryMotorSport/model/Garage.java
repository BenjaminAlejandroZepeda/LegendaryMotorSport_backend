package com.MotorSport.LegendaryMotorSport.model;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidad que representa un vehículo comprado por un usuario y almacenado en su garaje")
@Entity
@Table(name = "garage")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Garage {

    @Schema(description = "ID único del registro en el garaje, autoincremental", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Usuario propietario del vehículo en el garaje")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Schema(description = "Vehículo almacenado en el garaje")
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Schema(description = "Fecha en que se realizó la compra del vehículo", example = "2025-11-10T01:00:00")
    @Column(nullable = false)
    private LocalDateTime fechaCompra;
}
