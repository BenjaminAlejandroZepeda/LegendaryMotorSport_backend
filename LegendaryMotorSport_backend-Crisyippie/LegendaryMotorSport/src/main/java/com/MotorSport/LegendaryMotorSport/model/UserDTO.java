package com.MotorSport.LegendaryMotorSport.model;

import java.time.LocalDateTime;


public class UserDTO {
    private String username;
    private String email;
    private LocalDateTime lastLogin;

    public UserDTO(String username, String email, LocalDateTime lastLogin) {
        this.username = username;
        this.email = email;
        this.lastLogin = lastLogin;
    }

    public String getUsername() { return username; }
    public String getEmail() { return email; }
    public LocalDateTime getLastLogin() { return lastLogin; }
}