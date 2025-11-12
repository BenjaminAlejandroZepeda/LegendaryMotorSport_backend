package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VehicleService {

    private final VehicleRepository vehicleRepository;
    

    public VehicleService(VehicleRepository vehicleRepository) {
        this.vehicleRepository = vehicleRepository;
    }

    public List<Vehicle> getAllVehicles() {
        return vehicleRepository.findAll();
    }

    public Optional<Vehicle> getVehicleById(String id) {
        return vehicleRepository.findById(id);
    }

    public Vehicle saveVehicle(Vehicle vehicle) {
        return vehicleRepository.save(vehicle);
    }

    public Vehicle updateVehicle(String id, Vehicle updatedVehicle) {
    return vehicleRepository.findById(id)
        .map(existing -> {
            existing.setManufacturer(updatedVehicle.getManufacturer());
            existing.setModel(updatedVehicle.getModel());
            existing.setSeats(updatedVehicle.getSeats());
            existing.setPrice(updatedVehicle.getPrice());
            existing.setTopSpeed(updatedVehicle.getTopSpeed());
            existing.setImages(updatedVehicle.getImages());
            return vehicleRepository.save(existing);
        })
        .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));
}


    public void deleteVehicle(String id) {
        vehicleRepository.deleteById(id);
    }
}