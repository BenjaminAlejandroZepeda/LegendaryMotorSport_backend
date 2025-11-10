package com.MotorSport.LegendaryMotorSport.controllerTest;

import com.MotorSport.LegendaryMotorSport.controller.OrderController;
import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.model.orderModel.OrderItem;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.service.OrderService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.core.userdetails.User.withUsername;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrderController.class)
@Import(OrderControllerTest.TestSecurityConfig.class)
class OrderControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private Order order;

    private final String USERNAME = "benja";
    private final String PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        Vehicle vehicle = new Vehicle();
        vehicle.setId("veh123");
        vehicle.setPrice(1000000);

        OrderItem item = new OrderItem();
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

    @TestConfiguration
    static class TestSecurityConfig {
        @Bean
        public UserDetailsService userDetailsService() {
            return new InMemoryUserDetailsManager(
                    withUsername("benja")
                            .password("{noop}1234")
                            .roles("USER")
                            .build()
            );
        }

        @Bean
        public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
            http
                .csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic();
            return http.build();
        }
    }

    @Test
    void testCreateOrder() throws Exception {
        when(orderService.saveOrder(any(Order.class))).thenReturn(order);

        mockMvc.perform(post("/api/orders")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(order)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.estado").value("pendiente"));
    }

    @Test
    void testGetOrderById_Found() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));

        mockMvc.perform(get("/api/orders/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.direccionEnvio").value("Av. Siempre Viva 123"));
    }

    @Test
    void testGetOrderById_NotFound() throws Exception {
        when(orderService.getOrderById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/orders/99")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetOrdersByUserId() throws Exception {
        when(orderService.getOrdersByUserId(1L)).thenReturn(List.of(order));

        mockMvc.perform(get("/api/orders/user/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].estado").value("pendiente"));
    }

    @Test
    void testDeleteOrder_Found() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        doNothing().when(orderService).deleteOrder(1L);

        mockMvc.perform(delete("/api/orders/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testDeleteOrder_NotFound() throws Exception {
        when(orderService.getOrderById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(delete("/api/orders/99")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
    }
}
