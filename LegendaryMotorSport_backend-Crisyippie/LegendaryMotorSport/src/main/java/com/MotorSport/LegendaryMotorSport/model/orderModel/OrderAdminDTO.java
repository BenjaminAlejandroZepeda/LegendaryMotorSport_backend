package com.MotorSport.LegendaryMotorSport.model.orderModel;

import java.time.LocalDateTime;

public class OrderAdminDTO {
    private Long id;
    private String username;
    private String vehicleModel;
    private int price;
    private LocalDateTime fechaPedido;
    private String estado;
    private String direccionEnvio;

    public OrderAdminDTO(Long id, String username, String vehicleModel, int price,
                         LocalDateTime fechaPedido, String estado, String direccionEnvio) {
        this.id = id;
        this.username = username;
        this.vehicleModel = vehicleModel;
        this.price = price;
        this.fechaPedido = fechaPedido;
        this.estado = estado;
        this.direccionEnvio = direccionEnvio;
    }

    public Long getId() { return id; }
    public String getUsername() { return username; }
    public String getVehicleModel() { return vehicleModel; }
    public int getPrice() { return price; }
    public LocalDateTime getFechaPedido() { return fechaPedido; }
    public String getEstado() { return estado; }
    public String getDireccionEnvio() { return direccionEnvio; }


}
