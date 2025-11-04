package com.MotorSport.LegendaryMotorSport.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.MotorSport.LegendaryMotorSport.model.User;

import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByUsername(String username);
    Optional<User> findByEmail(String email);
}

