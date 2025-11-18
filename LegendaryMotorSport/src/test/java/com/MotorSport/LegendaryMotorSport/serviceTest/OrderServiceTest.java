package com.MotorSport.LegendaryMotorSport.serviceTest;

import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.model.orderModel.OrderItem;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.OrderRepository;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import com.MotorSport.LegendaryMotorSport.service.OrderService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class OrderServiceTest {

    @Mock
    private OrderRepository orderRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private OrderService orderService;

    private Order order;
    private Vehicle vehicle;
    private OrderItem item;

    @BeforeEach
    void setUp() {
        vehicle = new Vehicle();
        vehicle.setId("veh123");
        vehicle.setPrice(1000000);

        item = new OrderItem();
        item.setVehicle(vehicle);
        item.setCantidad(2);

        order = new Order();
        order.setId(1L);
        order.setUserId(1L);
        order.setFechaPedido(LocalDateTime.now());
        order.setEstado("pendiente");
        order.setDireccionEnvio("Av. Siempre Viva 123");
        order.setItems(List.of(item));
    }

    @Test
    void testSaveOrder_Success() {
        when(vehicleRepository.findById("veh123")).thenReturn(Optional.of(vehicle));
        when(orderRepository.save(any(Order.class))).thenReturn(order);

        Order saved = orderService.saveOrder(order);

        assertNotNull(saved);
        assertEquals(1000000, saved.getItems().get(0).getPrecioUnitario());
        assertEquals(2000000, saved.getItems().get(0).getPrecioTotal());
        verify(orderRepository).save(order);
    }

    @Test
    void testSaveOrder_EmptyItems() {
        order.setItems(List.of());

        Exception ex = assertThrows(IllegalArgumentException.class, () -> orderService.saveOrder(order));
        assertEquals("El pedido no contiene ítems.", ex.getMessage());
    }

    @Test
    void testSaveOrder_VehicleNotFound() {
        when(vehicleRepository.findById("veh123")).thenReturn(Optional.empty());

        Exception ex = assertThrows(RuntimeException.class, () -> orderService.saveOrder(order));
        assertTrue(ex.getMessage().contains("Vehículo no encontrado"));
    }

    @Test
    void testGetOrderById_Found() {
        when(orderRepository.findById(1L)).thenReturn(Optional.of(order));

        Optional<Order> result = orderService.getOrderById(1L);

        assertTrue(result.isPresent());
        verify(orderRepository).findById(1L);
    }

    @Test
    void testGetOrdersByUserId() {
        when(orderRepository.findByUserId(1L)).thenReturn(List.of(order));

        List<Order> result = orderService.getOrdersByUserId(1L);

        assertEquals(1, result.size());
        verify(orderRepository).findByUserId(1L);
    }

    @Test
    void testDeleteOrder() {
        doNothing().when(orderRepository).deleteById(1L);

        orderService.deleteOrder(1L);

        verify(orderRepository).deleteById(1L);
    }
}
