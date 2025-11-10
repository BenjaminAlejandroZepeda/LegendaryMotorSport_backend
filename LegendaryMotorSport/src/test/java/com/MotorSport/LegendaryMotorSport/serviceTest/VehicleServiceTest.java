package com.MotorSport.LegendaryMotorSport.serviceTest;

import com.MotorSport.LegendaryMotorSport.model.vehicleModel.TopSpeed;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.VehicleImages;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import com.MotorSport.LegendaryMotorSport.service.VehicleService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class VehicleServiceTest {

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private VehicleService vehicleService;

    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        TopSpeed topSpeed = new TopSpeed(null, 200, 320);
        VehicleImages images = new VehicleImages(null,
                "http://example.com/frontQuarter.jpg",
                "http://example.com/rearQuarter.jpg",
                "http://example.com/front.jpg",
                "http://example.com/rear.jpg",
                "http://example.com/side.jpg");

        vehicle = new Vehicle("veh123", "Pegassi", "Infernus", 2, 2000000, topSpeed, images);
    }

    @Test
    void testGetAllVehicles() {
        when(vehicleRepository.findAll()).thenReturn(Arrays.asList(vehicle));

        List<Vehicle> result = vehicleService.getAllVehicles();

        assertEquals(1, result.size());
        assertEquals("Infernus", result.get(0).getModel());
        assertEquals("Pegassi", result.get(0).getManufacturer());
        verify(vehicleRepository).findAll();
    }

    @Test
    void testGetVehicleById_Found() {
        when(vehicleRepository.findById("veh123")).thenReturn(Optional.of(vehicle));

        Optional<Vehicle> result = vehicleService.getVehicleById("veh123");

        assertTrue(result.isPresent());
        assertEquals(320, result.get().getTopSpeed().getKmh());
        assertEquals("http://example.com/front.jpg", result.get().getImages().getFront());
        verify(vehicleRepository).findById("veh123");
    }

    @Test
    void testGetVehicleById_NotFound() {
        when(vehicleRepository.findById("veh999")).thenReturn(Optional.empty());

        Optional<Vehicle> result = vehicleService.getVehicleById("veh999");

        assertFalse(result.isPresent());
        verify(vehicleRepository).findById("veh999");
    }

    @Test
    void testSaveVehicle() {
        when(vehicleRepository.save(vehicle)).thenReturn(vehicle);

        Vehicle saved = vehicleService.saveVehicle(vehicle);

        assertNotNull(saved);
        assertEquals("Infernus", saved.getModel());
        assertEquals(200, saved.getTopSpeed().getMph());
        assertEquals("http://example.com/side.jpg", saved.getImages().getSide());
        verify(vehicleRepository).save(vehicle);
    }

    @Test
    void testDeleteVehicle() {
        doNothing().when(vehicleRepository).deleteById("veh123");

        vehicleService.deleteVehicle("veh123");

        verify(vehicleRepository).deleteById("veh123");
    }
}