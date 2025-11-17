package com.MotorSport.LegendaryMotorSport.controller;


import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.Garage.Garage;
import com.MotorSport.LegendaryMotorSport.model.Garage.GarageRequest;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.service.GarageService;
import com.MotorSport.LegendaryMotorSport.service.UserService;
import com.MotorSport.LegendaryMotorSport.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/garage")
public class GarageController {

    @Autowired
    private GarageService garageService;

    @Autowired
    private UserService userService;

    @Autowired
    private VehicleService vehicleService;

    @PostMapping("/add")
    public ResponseEntity<Garage> addToGarage(
            @RequestBody GarageRequest request,
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        User user = userService.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Vehicle vehicle = vehicleService.getVehicleById(request.getVehicleId())
                .orElseThrow(() -> new RuntimeException("Vehicle not found: " + request.getVehicleId()));

        Garage item = new Garage();
        item.setUser(user);
        item.setVehicle(vehicle);
        item.setFechaCompra(LocalDateTime.now());

        Garage nuevoItem = garageService.addToGarage(item);
        return ResponseEntity.ok(nuevoItem);
    }

    @GetMapping
    public ResponseEntity<List<Garage>> getGarageForCurrentUser(
            @AuthenticationPrincipal UserDetails currentUser
    ) {
        User user = userService.findByEmail(currentUser.getUsername())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        return ResponseEntity.ok(garageService.getGarageByUser(user.getId()));
    }
}
