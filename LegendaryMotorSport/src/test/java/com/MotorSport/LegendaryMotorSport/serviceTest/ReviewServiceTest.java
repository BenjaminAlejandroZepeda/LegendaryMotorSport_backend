package com.MotorSport.LegendaryMotorSport.serviceTest;

import com.MotorSport.LegendaryMotorSport.model.Review;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.ReviewRepository;
import com.MotorSport.LegendaryMotorSport.service.ReviewService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    private Review review;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId("veh123");

        review = new Review(null, user, vehicle, "Excelente", 5, LocalDateTime.now());
    }

    @Test
    void testCreateReview() {
        when(reviewRepository.save(any(Review.class))).thenReturn(review);

        Review result = reviewService.createReview(review);

        assertNotNull(result);
        assertEquals("Excelente", result.getComentario());
        verify(reviewRepository).save(any(Review.class));
    }

    @Test
    void testGetAllReviews() {
        when(reviewRepository.findAll()).thenReturn(List.of(review));

        List<Review> result = reviewService.getAllReviews();

        assertEquals(1, result.size());
        verify(reviewRepository).findAll();
    }

    @Test
    void testGetReviewById_Found() {
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));

        Optional<Review> result = reviewService.getReviewById(1L);

        assertTrue(result.isPresent());
        verify(reviewRepository).findById(1L);
    }

    @Test
    void testGetReviewById_NotFound() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Review> result = reviewService.getReviewById(99L);

        assertFalse(result.isPresent());
        verify(reviewRepository).findById(99L);
    }

    @Test
    void testGetReviewsByVehicleId() {
        when(reviewRepository.findByVehicleId("veh123")).thenReturn(List.of(review));

        List<Review> result = reviewService.getReviewsByVehicleId("veh123");

        assertEquals(1, result.size());
        verify(reviewRepository).findByVehicleId("veh123");
    }

    @Test
    void testGetReviewsByUserId() {
        when(reviewRepository.findByUserId(1L)).thenReturn(List.of(review));

        List<Review> result = reviewService.getReviewsByUserId(1L);

        assertEquals(1, result.size());
        verify(reviewRepository).findByUserId(1L);
    }

    @Test
    void testDeleteReview() {
        doNothing().when(reviewRepository).deleteById(1L);

        reviewService.deleteReview(1L);

        verify(reviewRepository).deleteById(1L);
    }

    @Test
    void testUpdateReview_Found() {
        Review updated = new Review(null, review.getUser(), review.getVehicle(), "Mejorado", 4, null);
        when(reviewRepository.findById(1L)).thenReturn(Optional.of(review));
        when(reviewRepository.save(any(Review.class))).thenReturn(updated);

        Optional<Review> result = reviewService.updateReview(1L, updated);

        assertTrue(result.isPresent());
        assertEquals("Mejorado", result.get().getComentario());
        assertEquals(4, result.get().getPuntuacion());
    }

    @Test
    void testUpdateReview_NotFound() {
        when(reviewRepository.findById(99L)).thenReturn(Optional.empty());

        Optional<Review> result = reviewService.updateReview(99L, review);

        assertFalse(result.isPresent());
    }
}