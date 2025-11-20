package com.MotorSport.LegendaryMotorSport.serviceTest;

import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.Garage.Garage;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.GarageRepository;
import com.MotorSport.LegendaryMotorSport.service.GarageService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GarageServiceTest {

    @Mock
    private GarageRepository garageRepository;

    @InjectMocks
    private GarageService garageService;

    private Garage garage;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId("veh123");

        garage = new Garage(null, user, vehicle, LocalDateTime.now());
    }

    @Test
    void testAddToGarage() {
        when(garageRepository.save(any(Garage.class))).thenReturn(garage);

        Garage result = garageService.addToGarage(garage);

        assertNotNull(result);
        assertEquals("veh123", result.getVehicle().getId());
        verify(garageRepository).save(any(Garage.class));
    }

    @Test
    void testGetGarageByUser() {
        when(garageRepository.findByUserId(1L)).thenReturn(List.of(garage));

        List<Garage> result = garageService.getGarageByUser(1L);

        assertEquals(1, result.size());
        verify(garageRepository).findByUserId(1L);
    }

    @Test
    void testIsVehicleInGarage_True() {
        when(garageRepository.existsByUserIdAndVehicleId(1L, "veh123")).thenReturn(true);

        boolean exists = garageService.isVehicleInGarage(1L, "veh123");

        assertTrue(exists);
        verify(garageRepository).existsByUserIdAndVehicleId(1L, "veh123");
    }

    @Test
    void testIsVehicleInGarage_False() {
        when(garageRepository.existsByUserIdAndVehicleId(1L, "veh999")).thenReturn(false);

        boolean exists = garageService.isVehicleInGarage(1L, "veh999");

        assertFalse(exists);
        verify(garageRepository).existsByUserIdAndVehicleId(1L, "veh999");
    }
}
