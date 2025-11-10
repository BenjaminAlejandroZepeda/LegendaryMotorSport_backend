package com.MotorSport.LegendaryMotorSport.repository;

import com.MotorSport.LegendaryMotorSport.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {
    List<Review> findByVehicleId(String vehicleId);
    List<Review> findByUserId(Long userId);
}