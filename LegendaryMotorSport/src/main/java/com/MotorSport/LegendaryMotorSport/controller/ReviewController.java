package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.Review;
import com.MotorSport.LegendaryMotorSport.service.ReviewService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@Tag(name = "Reseñas", description = "Operaciones para crear, consultar, actualizar y eliminar reseñas de vehículos")
@RestController
@RequestMapping("/api/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @Operation(summary = "Crea una nueva reseña para un vehículo")
    @ApiResponse(responseCode = "200", description = "Reseña creada exitosamente")
    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review nuevaReview = reviewService.createReview(review);
        return ResponseEntity.ok(nuevaReview);
    }

    @Operation(summary = "Actualiza una reseña existente")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Reseña actualizada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        return reviewService.getReviewById(id)
                .map(existingReview -> {
                    existingReview.setComentario(updatedReview.getComentario());
                    existingReview.setPuntuacion(updatedReview.getPuntuacion());
                    existingReview.setFecha(LocalDateTime.now());
                    Review savedReview = reviewService.createReview(existingReview);
                    return ResponseEntity.ok(savedReview);
                })
                .orElse(ResponseEntity.notFound().build());
    }

    @Operation(summary = "Obtiene todas las reseñas registradas")
    @ApiResponse(responseCode = "200", description = "Listado de reseñas obtenido correctamente")
    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @Operation(summary = "Obtiene reseñas asociadas a un vehículo específico")
    @ApiResponse(responseCode = "200", description = "Listado de reseñas del vehículo obtenido correctamente")
    @GetMapping("/vehiculo/{vehicleId}")
    public ResponseEntity<List<Review>> getReviewsByVehicle(@PathVariable String vehicleId) {
        return ResponseEntity.ok(reviewService.getReviewsByVehicleId(vehicleId));
    }

    @Operation(summary = "Obtiene reseñas realizadas por un usuario específico")
    @ApiResponse(responseCode = "200", description = "Listado de reseñas del usuario obtenido correctamente")
    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

    @Operation(summary = "Elimina una reseña por su ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "204", description = "Reseña eliminada exitosamente"),
        @ApiResponse(responseCode = "404", description = "Reseña no encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
