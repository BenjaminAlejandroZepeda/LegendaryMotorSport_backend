package com.MotorSport.LegendaryMotorSport.controllerTest;

import com.MotorSport.LegendaryMotorSport.controller.UserController;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;
import org.springframework.boot.test.context.TestConfiguration; 
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.security.core.userdetails.User.*;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.httpBasic;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(UserController.class)
@Import(UserControllerTest.TestSecurityConfig.class)
class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    private User user;

    private final String USERNAME = "benja";
    private final String PASSWORD = "1234";

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);
        user.setUsername("benjamin");
        user.setEmail("benja@example.com");
        user.setPassword("12345");
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
                .authorizeHttpRequests(auth -> auth
                    .anyRequest().authenticated()
                )
                .httpBasic(); 
            return http.build();
        }
    }

    @Test
    void testRegisterUser() throws Exception {
        when(userService.saveUser(any(User.class))).thenReturn(user);

        mockMvc.perform(post("/api/users/register")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("benjamin"))
                .andExpect(jsonPath("$.email").value("benja@example.com"));

        verify(userService, times(1)).saveUser(any(User.class));
    }

    @Test
    void testLoginSuccess() throws Exception {
        when(userService.findByUsername("benjamin")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/users/login")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(user)))
                .andExpect(status().isOk())
                .andExpect(content().string("Login exitoso"));
    }

    @Test
    void testLoginWrongPassword() throws Exception {
        User wrong = new User();
        wrong.setUsername("benjamin");
        wrong.setPassword("incorrecta");

        when(userService.findByUsername("benjamin")).thenReturn(Optional.of(user));

        mockMvc.perform(post("/api/users/login")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(wrong)))
                .andExpect(status().isUnauthorized())
                .andExpect(content().string("Contraseña incorrecta"));
    }

    @Test
    void testLoginUserNotFound() throws Exception {
        when(userService.findByUsername("inexistente")).thenReturn(Optional.empty());

        User req = new User();
        req.setUsername("inexistente");
        req.setPassword("123");

        mockMvc.perform(post("/api/users/login")
                        .with(httpBasic(USERNAME, PASSWORD))
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(req)))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Usuario no encontrado"));
    }

    @Test
    void testGetUserById_Found() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("benjamin"))
                .andExpect(jsonPath("$.email").value("benja@example.com"));
    }

    @Test
    void testGetUserById_NotFound() throws Exception {
        when(userService.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/1")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserByUsername_Found() throws Exception {
        when(userService.findByUsername("benjamin")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/username/benjamin")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.email").value("benja@example.com"));
    }

    @Test
    void testGetUserByUsername_NotFound() throws Exception {
        when(userService.findByUsername("pepe")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/username/pepe")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetUserByEmail_Found() throws Exception {
        when(userService.findByEmail("benja@example.com")).thenReturn(Optional.of(user));

        mockMvc.perform(get("/api/users/email/benja@example.com")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("benjamin"));
    }

    @Test
    void testGetUserByEmail_NotFound() throws Exception {
        when(userService.findByEmail("nada@example.com")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/users/email/nada@example.com")
                        .with(httpBasic(USERNAME, PASSWORD)))
                .andExpect(status().isNotFound());
    }
}
