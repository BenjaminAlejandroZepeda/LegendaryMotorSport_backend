package com.MotorSport.LegendaryMotorSport.model.factura;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "detalle_factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleFactura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "factura_id")
    @JsonBackReference
    private Factura factura;

    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    private int precioUnitario;

    private int precioTotal;

    private Integer cantidad;
}
