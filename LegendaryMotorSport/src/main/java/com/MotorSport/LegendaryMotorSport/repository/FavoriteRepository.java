package com.MotorSport.LegendaryMotorSport.repository;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FavoriteRepository extends JpaRepository<Favorite, Long> {

    Favorite findByUserIdAndVehicleId(Long userId, String vehicleId);
    List<Favorite> findByUserId(Long userId);
}
