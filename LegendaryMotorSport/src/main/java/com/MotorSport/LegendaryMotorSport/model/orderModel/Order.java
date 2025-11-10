package com.MotorSport.LegendaryMotorSport.model.orderModel;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonManagedReference;

@Schema(description = "Entidad que representa una orden de compra realizada por un usuario")
@Entity
@Table(name = "orders")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

    @Schema(description = "ID único de la orden", example = "101")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "ID del usuario que realizó la orden", example = "5")
    @Column(nullable = false)
    private Long userId;

    @Schema(description = "Lista de ítems incluidos en la orden")
    @OneToMany(mappedBy = "order", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<OrderItem> items;

    @Schema(description = "Fecha en que se realizó la orden", example = "2025-11-10T01:00:00")
    @Column(nullable = false)
    private LocalDateTime fechaPedido;

    @Schema(description = "Estado actual de la orden", example = "pendiente")
    @Column(nullable = false)
    private String estado;

    @Schema(description = "Dirección de envío asociada a la orden", example = "Av. Las Condes 12345, Santiago")
    @Column(nullable = false)
    private String direccionEnvio;
}
