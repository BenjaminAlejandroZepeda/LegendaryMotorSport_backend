package com.MotorSport.LegendaryMotorSport.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.MotorSport.LegendaryMotorSport.model.Garage.Garage;

import java.util.List;

@Repository
public interface GarageRepository extends JpaRepository<Garage, Long> {
    List<Garage> findByUserId(Long userId);
    boolean existsByUserIdAndVehicleId(Long userId, String vehicleId);
}
