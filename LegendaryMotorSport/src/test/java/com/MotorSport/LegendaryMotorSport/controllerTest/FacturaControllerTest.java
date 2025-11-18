package com.MotorSport.LegendaryMotorSport.controllerTest;

import com.MotorSport.LegendaryMotorSport.controller.FacturaController;
import com.MotorSport.LegendaryMotorSport.model.factura.FacturaDTO;
import com.MotorSport.LegendaryMotorSport.model.orderModel.Order;
import com.MotorSport.LegendaryMotorSport.service.FacturaService;
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

import static org.mockito.Mockito.*;
import static org.springframework.security.core.userdetails.User.withUsername;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacturaController.class)
@Import(FacturaControllerTest.TestSecurityConfig.class)
class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FacturaService facturaService;

    @MockitoBean
    private OrderService orderService;

    @Autowired
    private ObjectMapper objectMapper;

    private FacturaDTO facturaDTO;
    private Order order;

    private final String USERNAME = "benja";
    private final String PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        facturaDTO = new FacturaDTO();
        facturaDTO.setId(1L);
        facturaDTO.setUserId(1L);
        facturaDTO.setFechaEmision(LocalDateTime.now());
        facturaDTO.setMetodoPago("Transferencia");
        facturaDTO.setTipoDocumento("boleta");
        facturaDTO.setMontoTotal(2000000.0);

        order = new Order();
        order.setId(1L);
        order.setUserId(1L);
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
            http.csrf(csrf -> csrf.disable())
                .authorizeHttpRequests(auth -> auth.anyRequest().authenticated())
                .httpBasic();
            return http.build();
        }
    }

    @Test
    void testGenerarFactura() throws Exception {
        when(orderService.getOrderById(1L)).thenReturn(Optional.of(order));
        when(facturaService.generarFacturaDesdeOrden(order, "Transferencia", "boleta")).thenReturn(facturaDTO);

        mockMvc.perform(post("/api/facturas/generar/1")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .param("metodoPago", "Transferencia")
                        .param("tipoDocumento", "boleta"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.montoTotal").value(2000000.0));
    }

    @Test
    void testObtenerFacturas() throws Exception {
        when(facturaService.obtenerTodasLasFacturas()).thenReturn(List.of(facturaDTO));

        mockMvc.perform(get("/api/facturas")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].userId").value(1));
    }

    @Test
    void testObtenerFacturasPorUsuario() throws Exception {
        when(facturaService.obtenerFacturasPorUsuario(1L)).thenReturn(List.of(facturaDTO));

        mockMvc.perform(get("/api/facturas/usuario/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].metodoPago").value("Transferencia"));
    }

    @Test
    void testEliminarFactura() throws Exception {
        doNothing().when(facturaService).eliminarFactura(1L);

        mockMvc.perform(delete("/api/facturas/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNoContent());

        verify(facturaService).eliminarFactura(1L);
    }
}
