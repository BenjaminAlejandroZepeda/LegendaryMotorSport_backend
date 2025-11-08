package com.MotorSport.LegendaryMotorSport.model.factura;

import lombok.Data;

@Data
public class DetalleFacturaDTO {
    private String vehicleId;
    private String modelo;
    private int precioUnitario;
    private int precioTotal;
    private Integer cantidad;
}
