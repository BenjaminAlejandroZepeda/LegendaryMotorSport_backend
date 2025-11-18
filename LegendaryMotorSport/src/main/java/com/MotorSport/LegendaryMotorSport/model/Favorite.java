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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Column(nullable = false)
    private LocalDateTime fechaGuardado;


    public Favorite(User user, Vehicle vehicle) {
        this.user = user;
        this.vehicle = vehicle;
        this.fechaGuardado = LocalDateTime.now();
    }
}
