package com.MotorSport.LegendaryMotorSport.controller;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.MotorSport.LegendaryMotorSport.model.Garage;
import com.MotorSport.LegendaryMotorSport.service.GarageService;

import java.util.List;

@RestController
@RequestMapping("/api/garage")
public class GarageController {

    @Autowired
    private GarageService garageItemService;

    
    @PostMapping
    public ResponseEntity<Garage> addToGarage(@RequestBody Garage item) {
        Garage nuevoItem = garageItemService.addToGarage(item);
        return ResponseEntity.ok(nuevoItem);
    }

   
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Garage>> getGarageByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(garageItemService.getGarageByUser(userId));
    }
}