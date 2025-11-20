package com.MotorSport.LegendaryMotorSport.model.factura;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "DTO que representa una factura generada a partir de una orden")
@Data
public class FacturaDTO {

    @Schema(description = "ID único de la factura", example = "1001")
    private Long id;

    @Schema(description = "ID del usuario que realizó la orden", example = "5")
    private Long userId;

    @Schema(description = "Fecha de emisión de la factura", example = "2025-11-10T01:00:00")
    private LocalDateTime fechaEmision;

    @Schema(description = "Monto total de la factura", example = "4000000.0")
    private Double montoTotal;

    @Schema(description = "Método de pago utilizado", example = "Transferencia")
    private String metodoPago;

    @Schema(description = "Tipo de documento generado", example = "boleta")
    private String tipoDocumento;

    @Schema(description = "Lista de ítems detallados en la factura")
    private List<DetalleFacturaDTO> detalle;
}
