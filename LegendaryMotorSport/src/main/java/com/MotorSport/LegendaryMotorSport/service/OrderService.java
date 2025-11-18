package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.orderModel.*;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.VehicleDTO;
import com.MotorSport.LegendaryMotorSport.repository.OrderRepository;
import com.MotorSport.LegendaryMotorSport.repository.UserRepository;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {

    private final OrderRepository orderRepository;
    private final VehicleRepository vehicleRepository;
    private final UserRepository userRepository;


    public OrderService(OrderRepository orderRepository, VehicleRepository vehicleRepository, UserRepository userRepository) {
        this.orderRepository = orderRepository;
        this.vehicleRepository = vehicleRepository;
        this.userRepository = userRepository;
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

  
    public OrderDTO mapToDTO(Order order) {
        OrderDTO dto = new OrderDTO();
        dto.setId(order.getId());
        dto.setUserId(order.getUserId());
        dto.setFechaPedido(order.getFechaPedido());
        dto.setEstado(order.getEstado());
        dto.setDireccionEnvio(order.getDireccionEnvio());
        dto.setItems(order.getItems().stream()
                .map(this::mapItemToDTO)
                .collect(Collectors.toList()));
        return dto;
    }

    private OrderItemDTO mapItemToDTO(OrderItem item) {
        OrderItemDTO dto = new OrderItemDTO();
        dto.setId(item.getId());
        dto.setCantidad(item.getCantidad());
        dto.setPrecioUnitario(item.getPrecioUnitario());
        dto.setPrecioTotal(item.getPrecioTotal());
        dto.setVehicle(mapVehicleToDTO(item.getVehicle()));
        return dto;
    }

    private VehicleDTO mapVehicleToDTO(Vehicle vehicle) {
        VehicleDTO dto = new VehicleDTO();
        dto.setId(vehicle.getId());
        dto.setManufacturer(vehicle.getManufacturer());
        dto.setModel(vehicle.getModel());
        dto.setPrice(vehicle.getPrice());
        dto.setSeats(vehicle.getSeats());
        dto.setTopSpeed(vehicle.getTopSpeed());
        dto.setImages(vehicle.getImages());
        return dto;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    public String getUsernameByUserId(Long userId) {
        return userRepository.findById(userId).map(User::getUsername).orElse("Desconocido");
    }

}
