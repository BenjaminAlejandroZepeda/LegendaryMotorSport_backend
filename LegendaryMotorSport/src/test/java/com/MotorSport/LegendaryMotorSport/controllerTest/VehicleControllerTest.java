package com.MotorSport.LegendaryMotorSport.controllerTest;

import com.MotorSport.LegendaryMotorSport.controller.VehicleController;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.TopSpeed;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.VehicleImages;
import com.MotorSport.LegendaryMotorSport.service.VehicleService;
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

import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.core.userdetails.User.withUsername;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(VehicleController.class)
@Import(VehicleControllerTest.TestSecurityConfig.class)
class VehicleControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private VehicleService vehicleService;

    @Autowired
    private ObjectMapper objectMapper;

    private Vehicle vehicle;

    private final String USERNAME = "benja";
    private final String PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        TopSpeed topSpeed = new TopSpeed(null, 200, 320);
        VehicleImages images = new VehicleImages(null,
                "http://example.com/frontQuarter.jpg",
                "http://example.com/rearQuarter.jpg",
                "http://example.com/front.jpg",
                "http://example.com/rear.jpg",
                "http://example.com/side.jpg");

        vehicle = new Vehicle("veh123", "Pegassi", "Infernus", 2, 2000000, topSpeed, images);
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
    void testGetAllVehicles() throws Exception {
        when(vehicleService.getAllVehicles()).thenReturn(List.of(vehicle));

        mockMvc.perform(get("/api/vehicles")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].model").value("Infernus"))
                .andExpect(jsonPath("$[0].manufacturer").value("Pegassi"));
    }

    @Test
    void testGetVehicleById_Found() throws Exception {
        when(vehicleService.getVehicleById("veh123")).thenReturn(Optional.of(vehicle));

        mockMvc.perform(get("/api/vehicles/veh123")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.model").value("Infernus"))
                .andExpect(jsonPath("$.topSpeed.kmh").value(320));
    }

    @Test
    void testGetVehicleById_NotFound() throws Exception {
        when(vehicleService.getVehicleById("veh999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/vehicles/veh999")
                .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(content().string("null"));
    }

    @Test
    void testSaveVehicles() throws Exception {
        Map<String, Vehicle> payload = Map.of("veh123", vehicle);
        when(vehicleService.saveVehicle(any(Vehicle.class))).thenReturn(vehicle);

        mockMvc.perform(post("/api/vehicles")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(payload)))
                .andExpect(status().isOk());

        verify(vehicleService).saveVehicle(any(Vehicle.class));
    }

    @Test
    void testDeleteVehicle() throws Exception {
        doNothing().when(vehicleService).deleteVehicle("veh123");

        mockMvc.perform(delete("/api/vehicles/veh123")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk());

        verify(vehicleService).deleteVehicle("veh123");
    }
}
