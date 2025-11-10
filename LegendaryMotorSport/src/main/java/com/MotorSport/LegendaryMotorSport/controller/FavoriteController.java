package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.service.FavoriteService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Tag(name = "Favoritos", description = "Operaciones para agregar, consultar y eliminar vehículos favoritos de un usuario")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    @Autowired
    private FavoriteService favoriteService;

    @Operation(summary = "Agrega un vehículo a los favoritos del usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo agregado exitosamente a favoritos"),
        @ApiResponse(responseCode = "400", description = "El vehículo ya está en favoritos")
    })
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

    @Operation(summary = "Obtiene todos los vehículos favoritos de un usuario")
    @ApiResponse(responseCode = "200", description = "Listado de favoritos obtenido correctamente")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Favorite>> getFavoritesByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(favoriteService.getFavoritesByUser(userId));
    }

    @Operation(summary = "Elimina un vehículo de los favoritos del usuario")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Vehículo eliminado de favoritos"),
        @ApiResponse(responseCode = "404", description = "Favorito no encontrado")
    })
    @DeleteMapping("/usuario/{userId}/vehiculo/{vehicleId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable Long userId, @PathVariable String vehicleId) {
        favoriteService.removeFavorite(userId, vehicleId);
        return ResponseEntity.noContent().build();
    }
}
