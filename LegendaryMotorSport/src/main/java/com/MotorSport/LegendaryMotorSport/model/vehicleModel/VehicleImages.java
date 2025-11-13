package com.MotorSport.LegendaryMotorSport.model.vehicleModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "URLs de imágenes del vehículo desde distintos ángulos")
@Entity
@Table(name = "vehicle_images")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VehicleImages {

    @Schema(description = "ID único del conjunto de imágenes", example = "1")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Imagen del vehículo en vista frontal diagonal", example = "https://vignette.wikia.nocookie.net/gtawiki/images/1/16/811-GTAO-FrontQuarter.png/revision/latest/scale-to-width-down/210")
    @Column(nullable = false)
    private String frontQuarter;

    @Schema(description = "Imagen del vehículo en vista trasera diagonal", example = "https://vignette.wikia.nocookie.net/gtawiki/images/f/fa/811-GTAO-RearQuarter.png/revision/latest/scale-to-width-down/210")
    @Column(nullable = false)
    private String rearQuarter;

    @Schema(description = "Imagen frontal directa del vehículo", example = "https://vignette.wikia.nocookie.net/gtawiki/images/9/9a/811-GTAO-Front.png/revision/latest/scale-to-width-down/210")
    @Column(nullable = false)
    private String front;

    @Schema(description = "Imagen trasera directa del vehículo", example = "https://vignette.wikia.nocookie.net/gtawiki/images/1/10/811-GTAO-Rear.png/revision/latest/scale-to-width-down/210")
    @Column(nullable = false)
    private String rear;

    @Schema(description = "Imagen lateral del vehículo", example = "https://vignette.wikia.nocookie.net/gtawiki/images/2/2c/811-GTAO-Side.png/revision/latest/scale-to-width-down/210")
    @Column(nullable = false)
    private String side;
}
