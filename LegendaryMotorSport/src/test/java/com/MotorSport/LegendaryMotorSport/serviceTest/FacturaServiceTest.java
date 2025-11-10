package com.MotorSport.LegendaryMotorSport.serviceTest;

import com.MotorSport.LegendaryMotorSport.model.factura.DetalleFactura;
import com.MotorSport.LegendaryMotorSport.model.factura.Factura;
import com.MotorSport.LegendaryMotorSport.model.factura.FacturaDTO;
import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.model.orderModel.OrderItem;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.FacturaRepository;
import com.MotorSport.LegendaryMotorSport.service.FacturaService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private FacturaService facturaService;

    private Order order;
    private Vehicle vehicle;
    private OrderItem item;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        vehicle = new Vehicle();
        vehicle.setId("veh123");
        vehicle.setModel("Infernus");
        vehicle.setPrice(1000000);

        item = new OrderItem();
        item.setVehicle(vehicle);
        item.setCantidad(2);
        item.setPrecioUnitario(1000000);
        item.setPrecioTotal(2000000);

        order = new Order();
        order.setId(1L);
        order.setUserId(1L);
        order.setFechaPedido(LocalDateTime.now());
        order.setEstado("pendiente");
        order.setDireccionEnvio("Av. Siempre Viva 123");
        order.setItems(List.of(item));
    }

    @Test
    void testGenerarFacturaDesdeOrden() {
        when(facturaRepository.save(any(Factura.class))).thenAnswer(invocation -> invocation.getArgument(0));

        FacturaDTO dto = facturaService.generarFacturaDesdeOrden(order, "Transferencia", "boleta");

        assertNotNull(dto);
        assertEquals(1L, dto.getUserId());
        assertEquals("Transferencia", dto.getMetodoPago());
        assertEquals("boleta", dto.getTipoDocumento());
        assertEquals(2000000.0, dto.getMontoTotal());
        assertEquals("veh123", dto.getDetalle().get(0).getVehicleId());
    }

    @Test
    void testObtenerTodasLasFacturas() {
        Factura factura = new Factura();
        factura.setId(1L);
        factura.setUserId(1L);
        factura.setFechaEmision(LocalDateTime.now());
        factura.setMetodoPago("Transferencia");
        factura.setTipoDocumento("boleta");
        factura.setMontoTotal(2000000.0);
        factura.setDetalle(List.of());

        when(facturaRepository.findAll()).thenReturn(List.of(factura));

        List<FacturaDTO> result = facturaService.obtenerTodasLasFacturas();

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void testObtenerFacturasPorUsuario() {
        Factura factura = new Factura();
        factura.setUserId(1L);
        factura.setDetalle(List.of());

        when(facturaRepository.findByUserId(1L)).thenReturn(List.of(factura));

        List<FacturaDTO> result = facturaService.obtenerFacturasPorUsuario(1L);

        assertEquals(1, result.size());
        assertEquals(1L, result.get(0).getUserId());
    }

    @Test
    void testEliminarFactura() {
        doNothing().when(facturaRepository).deleteById(1L);

        facturaService.eliminarFactura(1L);

        verify(facturaRepository).deleteById(1L);
    }
}
