package com.MotorSport.LegendaryMotorSport.model;

import java.time.LocalDateTime;

public class FavoriteDTO {
    private Long id;
    private String username;
    private String vehicleId;
    private String vehicleModel;
    private String manufacturer;
    private int seats;
    private int price;
    private Long userId;
    private LocalDateTime fechaGuardado;

    public FavoriteDTO(Long id, Long userId, String username, String vehicleId, String vehicleModel,
                   String manufacturer, int seats, int price, LocalDateTime fechaGuardado) {
    this.id = id;
    this.userId = userId;
    this.username = username;
    this.vehicleId = vehicleId;
    this.vehicleModel = vehicleModel;
    this.manufacturer = manufacturer;
    this.seats = seats;
    this.price = price;
    this.fechaGuardado = fechaGuardado;
}

    public LocalDateTime getFechaGuardado() { return fechaGuardado; }
    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getVehicleId() { return vehicleId; }
    public String getVehicleModel() { return vehicleModel; }
    public String getManufacturer() { return manufacturer; }
    public int getSeats() { return seats; }
    public int getPrice() { return price; }

    public void setId(Long id) { this.id = id; }
    public void setUsername(String username) { this.username = username; }
    public void setVehicleId(String vehicleId) { this.vehicleId = vehicleId; }
    public void setVehicleModel(String vehicleModel) { this.vehicleModel = vehicleModel; }
    public void setManufacturer(String manufacturer) { this.manufacturer = manufacturer; }
    public void setSeats(int seats) { this.seats = seats; }
    public void setPrice(int price) { this.price = price; }
    public void setFechaGuardado(LocalDateTime fechaGuardado) { this.fechaGuardado = fechaGuardado; }
    public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }

}
