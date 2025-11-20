package com.MotorSport.LegendaryMotorSport.controllerTest;

import com.MotorSport.LegendaryMotorSport.controller.ReviewController;
import com.MotorSport.LegendaryMotorSport.model.Review;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.service.ReviewService;
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

@WebMvcTest(ReviewController.class)
@Import(ReviewControllerTest.TestSecurityConfig.class)
class ReviewControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private ReviewService reviewService;

    @Autowired
    private ObjectMapper objectMapper;

    private Review review;

    private final String USERNAME = "benja";
    private final String PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId("veh123");

        review = new Review(1L, user, vehicle, "Excelente", 5, LocalDateTime.now());
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
    void testCreateReview() throws Exception {
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(post("/api/reviews")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.comentario").value("Excelente"));
    }

    @Test
    void testUpdateReview_Found() throws Exception {
        when(reviewService.getReviewById(1L)).thenReturn(Optional.of(review));
        when(reviewService.createReview(any(Review.class))).thenReturn(review);

        mockMvc.perform(put("/api/reviews/1")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.puntuacion").value(5));
    }

    @Test
    void testUpdateReview_NotFound() throws Exception {
        when(reviewService.getReviewById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(put("/api/reviews/99")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(review)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetAllReviews() throws Exception {
        when(reviewService.getAllReviews()).thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].comentario").value("Excelente"));
    }

    @Test
    void testGetReviewsByVehicle() throws Exception {
        when(reviewService.getReviewsByVehicleId("veh123")).thenReturn(List.of(review));

        mockMvc.perform(get("/api/reviews/vehiculo/veh123")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].puntuacion").value(5));
    }
}