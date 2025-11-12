package com.MotorSport.LegendaryMotorSport.model.vehicleModel;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Entidad que representa un vehículo disponible en el catálogo")
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Entity
@Table(name = "vehicles")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {

    @Schema(description = "ID único del vehículo", example = "veh123")
    @Id
    @Column(nullable = false, unique = true)
    private String id;

    @Schema(description = "Fabricante del vehículo", example = "Pegassi")
    @Column(nullable = false)
    private String manufacturer;

    @Schema(description = "Modelo del vehículo", example = "Infernus")
    @Column(nullable = false)
    private String model;

    @Schema(description = "Cantidad de asientos disponibles", example = "2")
    @Column(nullable = false)
    private int seats;

    @Schema(description = "Precio del vehículo en moneda local", example = "2000000")
    @Column(nullable = false)
    private int price;

    @Schema(description = "Objeto que representa la velocidad máxima del vehículo")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "top_speed_id", referencedColumnName = "id")
    private TopSpeed topSpeed;

    @Schema(description = "Objeto que contiene las imágenes del vehículo desde distintos ángulos")
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "images_id", referencedColumnName = "id")
    private VehicleImages images;
}
