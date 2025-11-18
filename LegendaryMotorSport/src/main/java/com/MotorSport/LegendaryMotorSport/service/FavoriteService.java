package com.MotorSport.LegendaryMotorSport.service;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.FavoriteRepository;
import com.MotorSport.LegendaryMotorSport.repository.UserRepository;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class FavoriteService {

    private final FavoriteRepository favoriteRepo;
    private final UserRepository userRepo;
    private final VehicleRepository vehicleRepo;

    public FavoriteService(FavoriteRepository favoriteRepo, UserRepository userRepo, VehicleRepository vehicleRepo) {
        this.favoriteRepo = favoriteRepo;
        this.userRepo = userRepo;
        this.vehicleRepo = vehicleRepo;
    }


    public Optional<Favorite> getFavorite(Long userId, String vehicleId) {
        return Optional.ofNullable(
                favoriteRepo.findByUserIdAndVehicleId(userId, vehicleId)
        );
    }


    public Favorite addFavorite(Favorite favorite) {

        Long userId = favorite.getUser().getId();
        String vehicleId = favorite.getVehicle().getId();

        User user = userRepo.findById(userId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        Vehicle vehicle = vehicleRepo.findById(vehicleId)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado"));

        Favorite nuevo = new Favorite();
        nuevo.setUser(user);
        nuevo.setVehicle(vehicle);
        nuevo.setFechaGuardado(LocalDateTime.now());

        return favoriteRepo.save(nuevo);
    }

 
    public List<Favorite> getFavoritesByUser(Long userId) {
        return favoriteRepo.findByUserId(userId);
    }


    public void removeFavorite(Long userId, String vehicleId) {
        Favorite existing = favoriteRepo.findByUserIdAndVehicleId(userId, vehicleId);
        if (existing != null) {
            favoriteRepo.delete(existing);
        }
    }

   
    public void toggleFavorite(Long userId, String vehicleId) {
        Favorite existing = favoriteRepo.findByUserIdAndVehicleId(userId, vehicleId);

        if (existing != null) {
            favoriteRepo.delete(existing);
            return;
        }

        User user = userRepo.findById(userId).orElseThrow();
        Vehicle vehicle = vehicleRepo.findById(vehicleId).orElseThrow();

        Favorite nuevo = new Favorite();
        nuevo.setUser(user);
        nuevo.setVehicle(vehicle);
        nuevo.setFechaGuardado(LocalDateTime.now());

        favoriteRepo.save(nuevo);
    }

    public List<Favorite> getAllFavorites() {
        return favoriteRepo.findAll();
    }

}
