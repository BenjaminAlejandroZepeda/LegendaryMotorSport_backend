package com.MotorSport.LegendaryMotorSport.model.factura;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Schema(description = "DTO que representa un ítem detallado dentro de una factura")
@Data
public class DetalleFacturaDTO {

    @Schema(description = "ID del vehículo facturado", example = "veh123")
    private String vehicleId;

    @Schema(description = "Modelo del vehículo", example = "Infernus")
    private String modelo;

    @Schema(description = "Precio unitario del vehículo", example = "2000000")
    private int precioUnitario;

    @Schema(description = "Precio total por este ítem", example = "2000000")
    private int precioTotal;

    @Schema(description = "Cantidad de unidades facturadas", example = "1")
    private Integer cantidad;
}
