package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.Garage;
import com.MotorSport.LegendaryMotorSport.service.GarageService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Garage", description = "Operaciones relacionadas con el garaje del usuario")
@RestController
@RequestMapping("/api/garage")
public class GarageController {

    @Autowired
    private GarageService garageItemService;

    @Operation(summary = "Agrega un vehículo al garaje del usuario")
    @ApiResponse(responseCode = "200", description = "Vehículo agregado exitosamente al garaje")
    @PostMapping
    public ResponseEntity<Garage> addToGarage(@RequestBody Garage item) {
        Garage nuevoItem = garageItemService.addToGarage(item);
        return ResponseEntity.ok(nuevoItem);
    }

    @Operation(summary = "Obtiene todos los vehículos del garaje de un usuario")
    @ApiResponse(responseCode = "200", description = "Listado de vehículos del garaje obtenido correctamente")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Garage>> getGarageByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(garageItemService.getGarageByUser(userId));
    }
}
