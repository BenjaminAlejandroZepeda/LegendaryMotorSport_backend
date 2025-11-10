package com.MotorSport.LegendaryMotorSport.model.factura;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.fasterxml.jackson.annotation.JsonBackReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Schema(description = "Entidad que representa un ítem detallado dentro de una factura persistida")
@Entity
@Table(name = "detalle_factura")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class DetalleFactura {

    @Schema(description = "ID único del ítem de factura", example = "501")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Factura a la que pertenece este ítem")
    @ManyToOne
    @JoinColumn(name = "factura_id")
    @JsonBackReference
    private Factura factura;

    @Schema(description = "Vehículo facturado")
    @ManyToOne
    @JoinColumn(name = "vehicle_id")
    private Vehicle vehicle;

    @Schema(description = "Precio unitario del vehículo", example = "2000000")
    private int precioUnitario;

    @Schema(description = "Precio total por este ítem", example = "2000000")
    private int precioTotal;

    @Schema(description = "Cantidad de unidades facturadas", example = "1")
    private Integer cantidad;
}
