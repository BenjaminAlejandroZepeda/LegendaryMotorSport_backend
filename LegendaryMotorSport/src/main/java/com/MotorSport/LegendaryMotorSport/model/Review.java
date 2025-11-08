package com.MotorSport.LegendaryMotorSport.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;

@Entity
@Table(name = "reviews")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(length = 1000, nullable = true)
    private String comentario;

    @Column(nullable = false)
    private int puntuacion; // Valor entre 1 y 5

    @Column(nullable = false)
    private LocalDateTime fecha;
}