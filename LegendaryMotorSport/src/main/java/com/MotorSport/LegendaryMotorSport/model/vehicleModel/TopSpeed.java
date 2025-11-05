package com.MotorSport.LegendaryMotorSport.model.vehicleModel;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "top_speeds")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSpeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private int mph;

    @Column(nullable = false)
    private int kmh;
}
