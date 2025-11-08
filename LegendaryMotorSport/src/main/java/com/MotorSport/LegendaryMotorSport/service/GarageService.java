package com.MotorSport.LegendaryMotorSport.service;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.MotorSport.LegendaryMotorSport.model.Garage;
import com.MotorSport.LegendaryMotorSport.repository.GarageRepository;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class GarageService {

    @Autowired
    private GarageRepository garageItemRepository;

    public Garage addToGarage(Garage item) {
        item.setFechaCompra(LocalDateTime.now());
        return garageItemRepository.save(item);
    }

    public List<Garage> getGarageByUser(Long userId) {
        return garageItemRepository.findByUserId(userId);
    }

    public boolean isVehicleInGarage(Long userId, String vehicleId) {
        return garageItemRepository.existsByUserIdAndVehicleId(userId, vehicleId);
    }
}