package com.MotorSport.LegendaryMotorSport.model.Garage;



public class GarageRequest {
    private String vehicleId;

    public GarageRequest() {}

    public GarageRequest(String vehicleId) {
        this.vehicleId = vehicleId;
    }

    public String getVehicleId() {
        return vehicleId;
    }

    public void setVehicleId(String vehicleId) {
        this.vehicleId = vehicleId;
    }
}
