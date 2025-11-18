package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.model.FavoriteDTO;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.service.FavoriteService;
import com.MotorSport.LegendaryMotorSport.repository.UserRepository;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;


import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Tag(name = "Favoritos", description = "Operaciones para agregar, consultar y eliminar vehículos favoritos de un usuario")
@RestController
@RequestMapping("/api/favorites")
public class FavoriteController {

    private final FavoriteService favoriteService;
    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;

    public FavoriteController(FavoriteService favoriteService, UserRepository userRepo, VehicleRepository vehicleRepo) {
        this.favoriteService = favoriteService;
        this.userRepo = userRepo;
        this.vehicleRepo = vehicleRepo;
    }

    @PostMapping
    @Operation(summary = "Agrega un vehículo a los favoritos del usuario autenticado")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Vehículo agregado exitosamente a favoritos"),
        @ApiResponse(responseCode = "400", description = "El vehículo ya está en favoritos"),
        @ApiResponse(responseCode = "403", description = "No autorizado")
    })
    public ResponseEntity<FavoriteDTO> addFavorite(
            @RequestBody Map<String, Object> body,
            Authentication authentication) {

        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).build();
        }

        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        String vehicleId = body.get("vehicleId").toString();

        Optional<Favorite> existing = favoriteService.getFavorite(user.getId(), vehicleId);
        if (existing.isPresent()) {
            return ResponseEntity.badRequest().build();
        }

        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Favorite saved = favoriteService.addFavorite(new Favorite(user, vehicle));

        FavoriteDTO dto = new FavoriteDTO(
                saved.getId(),
                saved.getUser().getId(),
                saved.getUser().getUsername(),
                saved.getVehicle().getId(),
                saved.getVehicle().getModel(),
                saved.getVehicle().getManufacturer(),
                saved.getVehicle().getSeats(),
                saved.getVehicle().getPrice(),
                saved.getFechaGuardado()
        );

        return ResponseEntity.ok(dto);
    }


    @Operation(summary = "Obtiene todos los vehículos favoritos de un usuario")
    @ApiResponse(responseCode = "200", description = "Listado de favoritos obtenido correctamente")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<FavoriteDTO>> getFavoritesByUser(@PathVariable Long userId) {
        List<Favorite> favoritos = favoriteService.getFavoritesByUser(userId);

        List<FavoriteDTO> dtos = favoritos.stream().map(f -> new FavoriteDTO(
                f.getId(),
                f.getUser().getId(),
                f.getUser().getUsername(),
                f.getVehicle().getId(),
                f.getVehicle().getModel(),
                f.getVehicle().getManufacturer(),
                f.getVehicle().getSeats(),
                f.getVehicle().getPrice(),
                f.getFechaGuardado()
        )).toList();

        return ResponseEntity.ok(dtos);
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

    @Operation(summary = "Obtiene todos los favoritos de todos los usuarios")
    @ApiResponse(responseCode = "200", description = "Listado de todos los favoritos obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<FavoriteDTO>> getAllFavorites() {
        List<Favorite> favoritos = favoriteService.getAllFavorites();

        List<FavoriteDTO> dtos = favoritos.stream().map(f -> new FavoriteDTO(
                f.getId(),
                f.getUser().getId(),
                f.getUser().getUsername(),
                f.getVehicle().getId(),
                f.getVehicle().getModel(),
                f.getVehicle().getManufacturer(),
                f.getVehicle().getSeats(),
                f.getVehicle().getPrice(),
                f.getFechaGuardado()
        )).toList();

        return ResponseEntity.ok(dtos);
    }

    @DeleteMapping("/vehiculo/{vehicleId}")
    public ResponseEntity<Void> removeFavorite(@PathVariable String vehicleId, Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).build();
        }

        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        favoriteService.removeFavorite(user.getId(), vehicleId);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/me")
    public ResponseEntity<List<Favorite>> getMyFavorites(Authentication authentication) {
        if (authentication == null || !authentication.isAuthenticated()) {
            return ResponseEntity.status(403).build();
        }

        String email = authentication.getName();
        User user = userRepo.findByEmail(email)
            .orElseThrow(() -> new RuntimeException("Usuario autenticado no encontrado"));

        List<Favorite> favoritos = favoriteService.getFavoritesByUser(user.getId());
        return ResponseEntity.ok(favoritos);
    }


}