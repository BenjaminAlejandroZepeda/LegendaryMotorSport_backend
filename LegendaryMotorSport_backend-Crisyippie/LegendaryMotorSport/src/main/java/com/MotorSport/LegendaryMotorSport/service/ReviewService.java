package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.Review;
import com.MotorSport.LegendaryMotorSport.repository.ReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ReviewService {

    @Autowired
    private ReviewRepository reviewRepository;

    public Review createReview(Review review) {
        review.setFecha(LocalDateTime.now());
        return reviewRepository.save(review);
    }

    public List<Review> getAllReviews() {
        return reviewRepository.findAll();
    }

    public Optional<Review> getReviewById(Long id) {
        return reviewRepository.findById(id);
    }

    public List<Review> getReviewsByVehicleId(String vehicleId) {
        return reviewRepository.findByVehicleId(vehicleId);
    }

    public List<Review> getReviewsByUserId(Long userId) {
        return reviewRepository.findByUserId(userId);
    }

    public void deleteReview(Long id) {
        reviewRepository.deleteById(id);
    }

    public Optional<Review> updateReview(Long id, Review updatedReview) {
        return reviewRepository.findById(id).map(existingReview -> {
            existingReview.setComentario(updatedReview.getComentario());
            existingReview.setPuntuacion(updatedReview.getPuntuacion());
            existingReview.setFecha(LocalDateTime.now());
            return reviewRepository.save(existingReview);
        });
    }
}