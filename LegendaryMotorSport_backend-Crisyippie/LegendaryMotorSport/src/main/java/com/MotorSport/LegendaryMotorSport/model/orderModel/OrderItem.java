package com.MotorSport.LegendaryMotorSport.model.orderModel;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import com.fasterxml.jackson.annotation.JsonBackReference;

@Schema(description = "Entidad que representa un ítem dentro de una orden de compra")
@Entity
@Table(name = "order_items")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {

    @Schema(description = "ID único del ítem de orden", example = "501")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Orden a la que pertenece este ítem")
    @ManyToOne
    @JoinColumn(name = "order_id", nullable = false)
    @JsonBackReference
    private Order order;

    @Schema(description = "Vehículo incluido en este ítem")
    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    @Schema(description = "Cantidad de unidades del vehículo", example = "1")
    @Column(nullable = false)
    private int cantidad;

    @Schema(description = "Precio unitario del vehículo", example = "2000000")
    @Column(nullable = false)
    private int precioUnitario;

    @Schema(description = "Precio total por este ítem", example = "2000000")
    @Column(nullable = false)
    private int precioTotal;
}
