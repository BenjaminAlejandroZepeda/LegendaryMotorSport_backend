package com.MotorSport.LegendaryMotorSport.model.vehicleModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Velocidad máxima del vehículo en distintas unidades")
@Entity
@Table(name = "top_speeds")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopSpeed {

    @Schema(description = "ID único de la velocidad", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Velocidad máxima en millas por hora", example = "200")
    @Column(nullable = false)
    private int mph;

    @Schema(description = "Velocidad máxima en kilómetros por hora", example = "320")
    @Column(nullable = false)
    private int kmh;
}
