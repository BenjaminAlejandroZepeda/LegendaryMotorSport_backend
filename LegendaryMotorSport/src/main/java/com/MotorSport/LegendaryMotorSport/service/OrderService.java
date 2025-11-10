package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.model.orderModel.OrderItem;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.OrderRepository;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final VehicleRepository vehicleRepository;


    public OrderService(OrderRepository orderRepository, VehicleRepository vehicleRepository) {
        this.orderRepository = orderRepository;
        this.vehicleRepository = vehicleRepository;
    }

    public Order saveOrder(Order order) {
        if (order.getItems() == null || order.getItems().isEmpty()) {
            throw new IllegalArgumentException("El pedido no contiene ítems.");
        }

        for (OrderItem item : order.getItems()) {
            String vehicleId = item.getVehicle().getId();

            Vehicle vehicle = vehicleRepository.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado: " + vehicleId));

            item.setVehicle(vehicle);
            item.setOrder(order);
            item.setPrecioUnitario(vehicle.getPrice());
            item.setPrecioTotal(vehicle.getPrice() * item.getCantidad());
        }

        return orderRepository.save(order);
    }

    public Optional<Order> getOrderById(Long id) {
        return orderRepository.findById(id);
    }

    public List<Order> getOrdersByUserId(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public void deleteOrder(Long id) {
        orderRepository.deleteById(id);
    }
}