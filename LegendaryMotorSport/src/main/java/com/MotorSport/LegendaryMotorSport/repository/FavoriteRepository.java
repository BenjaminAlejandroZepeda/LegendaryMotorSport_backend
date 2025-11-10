package com.MotorSport.LegendaryMotorSport.repository;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FavoriteRepository extends JpaRepository<Favorite, Long> {
    List<Favorite> findByUserId(Long userId);
    Optional<Favorite> findByUserIdAndVehicleId(Long userId, String vehicleId);
    void deleteByUserIdAndVehicleId(Long userId, String vehicleId);
}
