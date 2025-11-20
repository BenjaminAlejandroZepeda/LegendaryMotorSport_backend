package com.MotorSport.LegendaryMotorSport.model;

import java.time.LocalDateTime;

public class ReviewDTO {
    private Long id;
    private String comentario;
    private int puntuacion;
    private LocalDateTime fecha;
    private String username;
    private String vehicleModel;

    public ReviewDTO(Long id, String comentario, int puntuacion, LocalDateTime fecha, String username, String vehicleModel) {
        this.id = id;
        this.comentario = comentario;
        this.puntuacion = puntuacion;
        this.fecha = fecha;
        this.username = username;
        this.vehicleModel = vehicleModel;
    }

    public Long getId() { return id; }
    public String getComentario() { return comentario; }
    public int getPuntuacion() { return puntuacion; }
    public LocalDateTime getFecha() { return fecha; }
    public String getUsername() { return username; }
    public String getVehicleModel() { return vehicleModel; }

    public void setId(Long id) { this.id = id; }
    public void setComentario(String comentario) { this.comentario = comentario; }
    public void setPuntuacion(int puntuacion) { this.puntuacion = puntuacion; }
    public void setFecha(LocalDateTime fecha) { this.fecha = fecha; }
    public void setUsername(String username) { this.username = username; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }
}