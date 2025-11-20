package com.MotorSport.LegendaryMotorSport.model.orderModel;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO que representa una orden de compra")
@Data
public class OrderDTO {

    @Schema(description = "ID de la orden")
    private Long id;

    @Schema(description = "ID del usuario")
    private Long userId;

    @Schema(description = "Fecha de la orden")
    private LocalDateTime fechaPedido;

    @Schema(description = "Estado de la orden")
    private String estado;

    @Schema(description = "Dirección de envío")
    private String direccionEnvio;

    @Schema(description = "Lista de ítems de la orden")
    private List<OrderItemDTO> items;
}
