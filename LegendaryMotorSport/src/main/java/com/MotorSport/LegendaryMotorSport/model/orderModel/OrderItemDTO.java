package com.MotorSport.LegendaryMotorSport.model.orderModel;


import com.MotorSport.LegendaryMotorSport.model.vehicleModel.VehicleDTO;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO que representa un ítem dentro de una orden de compra")
@Data
public class OrderItemDTO {

    @Schema(description = "ID del ítem")
    private Long id;

    @Schema(description = "Cantidad de vehículos")
    private int cantidad;

    @Schema(description = "Precio unitario del vehículo")
    private int precioUnitario;

    @Schema(description = "Precio total de este ítem")
    private int precioTotal;

    @Schema(description = "Datos del vehículo")
    private VehicleDTO vehicle;
}

