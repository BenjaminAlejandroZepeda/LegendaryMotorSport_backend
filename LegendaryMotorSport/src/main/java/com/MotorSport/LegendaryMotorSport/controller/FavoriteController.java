package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.service.FavoriteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @PostMapping
    public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite favorite) {
        Optional<Favorite> existing = favoriteService.getFavorite(
                favorite.getUser().getId(),
                favorite.getVehicle().getId()
        );

        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build(); 
        }

        Favorite nuevoFavorito = favoriteService.addFavorite(favorite);
        return ResponseEntity.ok(nuevoFavorito);
    }

    
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Favorite>> getFavoritesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteService.getFavoritesByUser(userId));
    }

    @DeleteMapping("/usuario/{userId}/vehiculo/{vehicleId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable String vehicleId) {
        favoriteService.removeFavorite(userId, vehicleId);
        return ResponseEntity.noContent().build();
    }
}