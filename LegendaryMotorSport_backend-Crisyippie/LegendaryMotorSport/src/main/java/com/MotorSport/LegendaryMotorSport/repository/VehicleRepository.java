package com.MotorSport.LegendaryMotorSport.repository;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;

import java.util.List;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface VehicleRepository extends JpaRepository<Vehicle, String> {

    @EntityGraph(attributePaths = {"images", "topSpeed"})
    List<Vehicle> findAll();
}
