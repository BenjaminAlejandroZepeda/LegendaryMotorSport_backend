package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.service.VehicleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Vehículos", description = "Operaciones relacionadas con el catálogo de vehículos")
@RestController
@RequestMapping("/api/vehicles")
public class VehicleController {

    private final VehicleService vehicleService;

    public VehicleController(VehicleService vehicleService) {
        this.vehicleService = vehicleService;
    }

    @Operation(summary = "Obtiene todos los vehículos disponibles")
    @ApiResponse(responseCode = "200", description = "Lista de vehículos obtenida correctamente")
    @GetMapping
    public List<Vehicle> getAllVehicles() {
        return vehicleService.getAllVehicles();
    }

    @Operation(summary = "Obtiene un vehículo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo encontrado"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @GetMapping("/{id}")
    public Optional<Vehicle> getVehicleById(@PathVariable String id) {
        return vehicleService.getVehicleById(id);
    }

    @Operation(summary = "Guarda uno o más vehículos en el sistema")
    @ApiResponse(responseCode = "200", description = "Vehículos guardados correctamente")
    @PostMapping
    public void saveVehicles(@RequestBody Map<String, Vehicle> vehiclesMap) {
        vehiclesMap.forEach((key, vehicle) -> {
            vehicle.setId(key); 
            vehicleService.saveVehicle(vehicle);
        });
    }


    @Operation(summary = "Actualiza un vehículo existente por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo actualizado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Vehicle> updateVehicle(@PathVariable String id, @RequestBody Vehicle updatedVehicle) {
        Optional<Vehicle> existingOpt = vehicleService.getVehicleById(id);

        if (existingOpt.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        Vehicle existing = existingOpt.get();

        existing.setManufacturer(updatedVehicle.getManufacturer());
        existing.setModel(updatedVehicle.getModel());
        existing.setSeats(updatedVehicle.getSeats());
        existing.setPrice(updatedVehicle.getPrice());
        existing.setTopSpeed(updatedVehicle.getTopSpeed());
        existing.setImages(updatedVehicle.getImages());

        Vehicle saved = vehicleService.saveVehicle(existing);
        return ResponseEntity.ok(saved);
    }


    @Operation(summary = "Elimina un vehículo por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado correctamente"),
        @ApiResponse(responseCode = "404", description = "Vehículo no encontrado")
    })
    @DeleteMapping("/{id}")
    public void deleteVehicle(@PathVariable String id) {
        vehicleService.deleteVehicle(id);
    }
}