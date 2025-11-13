package com.MotorSport.LegendaryMotorSport.model.factura;

import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Schema(description = "Entidad que representa una factura persistida en base de datos")
@Entity
@Table(name = "facturas")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Factura {

    @Schema(description = "ID único de la factura", example = "1001")
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Schema(description = "Orden asociada a la factura")
    @OneToOne
    @JoinColumn(name = "order_id", referencedColumnName = "id")
    private Order order;

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
    @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL)
    @JsonManagedReference
    private List<DetalleFactura> detalle;
}
