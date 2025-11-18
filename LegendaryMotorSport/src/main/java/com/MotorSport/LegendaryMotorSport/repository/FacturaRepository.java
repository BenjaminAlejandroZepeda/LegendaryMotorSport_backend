package com.MotorSport.LegendaryMotorSport.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.MotorSport.LegendaryMotorSport.model.factura.Factura;

@Repository
public interface FacturaRepository extends JpaRepository<Factura, Long> {
    List<Factura> findByUserId(Long userId);
    Optional<Factura> findByOrderId(Long orderId);
}
