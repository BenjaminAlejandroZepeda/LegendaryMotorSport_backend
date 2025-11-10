package com.MotorSport.LegendaryMotorSport.model;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Schema(description = "Entidad que representa una reseña realizada por un usuario sobre un vehículo")
@Entity
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Schema(description = "ID único de la reseña", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Usuario que realizó la reseña")
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Schema(description = "Vehículo reseñado por el usuario")
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Schema(description = "Comentario escrito por el usuario", example = "Excelente rendimiento y diseño deportivo")
    @Column(length = 1000, nullable = true)
    private String comentario;

    @Schema(description = "Puntuación otorgada al vehículo (1 a 5)", example = "5")
    @Column(nullable = false)
    private int puntuacion;

    @Schema(description = "Fecha en que se realizó la reseña", example = "2025-11-10T01:00:00")
    @Column(nullable = false)
    private LocalDateTime fecha;
}
