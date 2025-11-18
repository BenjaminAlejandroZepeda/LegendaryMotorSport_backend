package com.MotorSport.LegendaryMotorSport.controllerTest;

import com.MotorSport.LegendaryMotorSport.controller.FavoriteController;
import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.service.FavoriteService;
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

@WebMvcTest(FavoriteController.class)
@Import(FavoriteControllerTest.TestSecurityConfig.class)
class FavoriteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private FavoriteService favoriteService;

    @Autowired
    private ObjectMapper objectMapper;

    private Favorite favorite;

    private final String USERNAME = "benja";
    private final String PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId("veh123");

        favorite = new Favorite(1L, user, vehicle, LocalDateTime.now());
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
    void testAddFavorite_New() throws Exception {
        when(favoriteService.getFavorite(1L, "veh123")).thenReturn(Optional.empty());
        when(favoriteService.addFavorite(any(Favorite.class))).thenReturn(favorite);

        mockMvc.perform(post("/api/favorites")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favorite)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.vehicle.id").value("veh123"));
    }

    @Test
    void testAddFavorite_AlreadyExists() throws Exception {
        when(favoriteService.getFavorite(1L, "veh123")).thenReturn(Optional.of(favorite));

        mockMvc.perform(post("/api/favorites")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(favorite)))
                .andExpect(status().isBadRequest());
    }

    @Test
    void testGetFavoritesByUser() throws Exception {
        when(favoriteService.getFavoritesByUser(1L)).thenReturn(List.of(favorite));

        mockMvc.perform(get("/api/favorites/usuario/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].user.id").value(1));
    }

    @Test
    void testRemoveFavorite() throws Exception {
        doNothing().when(favoriteService).removeFavorite(1L, "veh123");

        mockMvc.perform(delete("/api/favorites/usuario/1/vehiculo/veh123")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNoContent());

        verify(favoriteService).removeFavorite(1L, "veh123");
    }
}
