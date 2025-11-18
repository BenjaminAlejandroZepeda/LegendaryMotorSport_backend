package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.factura.FacturaDTO;
import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.service.FacturaService;
import com.MotorSport.LegendaryMotorSport.service.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Facturas", description = "Operaciones para generar, consultar y eliminar facturas")
@RestController
@RequestMapping("/api/facturas")
public class FacturaController {

    private final FacturaService facturaService;
    private final OrderService orderService;

    public FacturaController(FacturaService facturaService, OrderService orderService) {
        this.facturaService = facturaService;
        this.orderService = orderService;
    }

    @Operation(summary = "Genera una factura a partir de una orden")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura generada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Orden no encontrada")
    })
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

    @Operation(summary = "Obtiene todas las facturas registradas")
    @ApiResponse(responseCode = "200", description = "Listado de facturas obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<FacturaDTO>> obtenerFacturas() {
        return ResponseEntity.ok(facturaService.obtenerTodasLasFacturas());
    }

    @Operation(summary = "Obtiene las facturas asociadas a un usuario")
    @ApiResponse(responseCode = "200", description = "Listado de facturas del usuario obtenido correctamente")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<FacturaDTO>> obtenerFacturasPorUsuario(@PathVariable Long userId) {
        return ResponseEntity.ok(facturaService.obtenerFacturasPorUsuario(userId));
    }


    @Operation(summary = "Obtiene la factura asociada a una orden")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Factura encontrada"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada para la orden")
    })
    @GetMapping("/orden/{orderId}")
    public ResponseEntity<FacturaDTO> obtenerFacturaPorOrden(@PathVariable Long orderId) {
        FacturaDTO factura = facturaService.obtenerFacturaPorOrderId(orderId)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada para la orden con ID: " + orderId));
        return ResponseEntity.ok(factura);
    }


    @Operation(summary = "Elimina una factura por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Factura eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Factura no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }
}
