package com.MotorSport.LegendaryMotorSport.controller;

import com.MotorSport.LegendaryMotorSport.model.Review;
import com.MotorSport.LegendaryMotorSport.service.ReviewService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;


@RestController
@RequestMapping("/reviews")
public class ReviewController {

    @Autowired
    private ReviewService reviewService;

    @PostMapping
    public ResponseEntity<Review> createReview(@RequestBody Review review) {
        Review nuevaReview = reviewService.createReview(review);
        return ResponseEntity.ok(nuevaReview);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Review> updateReview(@PathVariable Long id, @RequestBody Review updatedReview) {
        return reviewService.getReviewById(id).map(existingReview -> {
            existingReview.setComentario(updatedReview.getComentario());
            existingReview.setPuntuacion(updatedReview.getPuntuacion());
            existingReview.setFecha(LocalDateTime.now()); 
            Review savedReview = reviewService.createReview(existingReview);
            return ResponseEntity.ok(savedReview);
        }).orElse(ResponseEntity.notFound().build());
    }

    @GetMapping
    public ResponseEntity<List<Review>> getAllReviews() {
        return ResponseEntity.ok(reviewService.getAllReviews());
    }

    @GetMapping("/vehiculo/{vehicleId}")
    public ResponseEntity<List<Review>> getReviewsByVehicle(@PathVariable String vehicleId) {
        return ResponseEntity.ok(reviewService.getReviewsByVehicleId(vehicleId));
    }


    @GetMapping("/usuario/{userId}")
    public ResponseEntity<List<Review>> getReviewsByUser(@PathVariable Long userId) {
        return ResponseEntity.ok(reviewService.getReviewsByUserId(userId));
    }

 
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReview(@PathVariable Long id) {
        reviewService.deleteReview(id);
        return ResponseEntity.noContent().build();
    }
}
