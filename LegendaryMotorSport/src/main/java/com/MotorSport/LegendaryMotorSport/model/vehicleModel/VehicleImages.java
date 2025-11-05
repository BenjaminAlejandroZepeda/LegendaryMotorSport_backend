package com.MotorSport.LegendaryMotorSport.model.vehicleModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "vehicle_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleImages {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String frontQuarter;

    @Column(nullable = false)
    private String rearQuarter;

    @Column(nullable = false)
    private String front;

    @Column(nullable = false)
    private String rear;

    @Column(nullable = false)
    private String side;
}