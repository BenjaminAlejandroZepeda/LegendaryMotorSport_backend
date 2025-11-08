package com.MotorSport.LegendaryMotorSport.model.factura;

import java.time.LocalDateTime;
import java.util.List;

import lombok.Data;

@Data
public class FacturaDTO {
    private Long id;
    private Long userId;
    private LocalDateTime fechaEmision;
    private Double montoTotal;
    private String metodoPago;
    private String tipoDocumento;
    private List<DetalleFacturaDTO> detalle;
}