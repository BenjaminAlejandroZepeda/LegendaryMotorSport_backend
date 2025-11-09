package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.factura.FacturaDTO;
import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.service.FacturaService;
import com.MotorSport.LegendaryMotorSport.service.OrderService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final OrderService orderService;

    public FacturaController(FacturaService facturaService, OrderService orderService) {
        this.facturaService = facturaService;
        this.orderService = orderService;
    }

    @PostMapping("/generar/{orderId}")
    public ResponseEntity<FacturaDTO> generarFactura(
            @PathVariable Long orderId,
            @RequestParam String metodoPago,
            @RequestParam String tipoDocumento) {

        Order order = orderService.getOrderById(orderId)
                .orElseThrow(() -> new RuntimeException("Orden no encontrada con ID: " + orderId));

        FacturaDTO factura = facturaService.generarFacturaDesdeOrden(order, metodoPago, tipoDocumento);
        return ResponseEntity.ok(factura);
    }

    @GetMapping
    public ResponseEntity<List<FacturaDTO>> obtenerFacturas() {
        return ResponseEntity.ok(facturaService.obtenerTodasLasFacturas());
    }

    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<FacturaDTO>> obtenerFacturasPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(facturaService.obtenerFacturasPorUsuario(userId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}