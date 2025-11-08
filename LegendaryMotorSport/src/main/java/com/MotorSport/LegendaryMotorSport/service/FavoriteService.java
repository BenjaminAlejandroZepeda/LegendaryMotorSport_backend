package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.repository.FavoriteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    @Autowired
    private FavoriteRepository favoriteRepository;

    public Favorite addFavorite(Favorite favorite) {
        favorite.setFechaGuardado(LocalDateTime.now());
        return favoriteRepository.save(favorite);
    }

    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepository.findByUserId(userId);
    }

    public Optional<Favorite> getFavorite(Long userId, String vehicleId) {
        return favoriteRepository.findByUserIdAndVehicleId(userId, vehicleId);
    }

    public void removeFavorite(Long userId, String vehicleId) {
        favoriteRepository.deleteByUserIdAndVehicleId(userId, vehicleId);
    }
}