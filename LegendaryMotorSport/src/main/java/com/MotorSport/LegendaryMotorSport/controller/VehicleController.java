package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.service.VehicleService;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;
   
    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    // GET /vehicles → lista todos los vehículos
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    // vehículo por ID
    @GetMapping("/{id}")
    public Optional<Vehicle> getVehicleById(@PathVariable String id) {
        return vehicleService.getVehicleById(id);
    }

    // POST /vehicles → guardar nuevo vehículo
    @PostMapping
    public void saveVehicles(@RequestBody Map<String, Vehicle> vehicles) {
        vehicles.forEach((id, vehicle) -> {
            vehicle.setId(id); // asigna el ID desde la clave del mapa
            vehicleService.saveVehicle(vehicle);
        });
    }

    // DELETE /vehicles/{id} → eliminar vehículo por ID
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
    }
}