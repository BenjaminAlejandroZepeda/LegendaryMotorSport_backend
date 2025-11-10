package com.MotorSport.LegendaryMotorSport.model.vehicleModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO que representa un vehículo del catálogo")
@Data
public class VehicleDTO {

    @Schema(description = "ID único del vehículo", example = "1")
    private String id;

    @Schema(description = "Fabricante del vehículo", example = "pfister")
    private String manufacturer;

    @Schema(description = "Modelo del vehículo", example = "pfister811")
    private String model;

    @Schema(description = "Cantidad de asientos del vehículo", example = "2")
    private int seats;

    @Schema(description = "Precio del vehículo en la moneda local", example = "1135000")
    private int price;

    @Schema(description = "Velocidad máxima del vehículo")
    private TopSpeed topSpeed;

    @Schema(description = "Imágenes del vehículo")
    private VehicleImages images;
}
