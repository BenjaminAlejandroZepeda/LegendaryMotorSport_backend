package com.MotorSport.LegendaryMotorSport.serviceTest;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.FavoriteRepository;
import com.MotorSport.LegendaryMotorSport.repository.UserRepository;
import com.MotorSport.LegendaryMotorSport.repository.VehicleRepository;
import com.MotorSport.LegendaryMotorSport.service.FavoriteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class FavoriteServiceTest {

    @Mock
    private FavoriteRepository favoriteRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private VehicleRepository vehicleRepository;

    @InjectMocks
    private FavoriteService favoriteService;

    private Favorite favorite;
    private User user;
    private Vehicle vehicle;

    @BeforeEach
    void setUp() {
        user = new User();
        user.setId(1L);

        vehicle = new Vehicle();
        vehicle.setId("veh123");

        favorite = new Favorite(null, user, vehicle, LocalDateTime.now());
    }

    @Test
    void testAddFavorite() {
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);

        Favorite result = favoriteService.addFavorite(favorite);

        assertNotNull(result);
        assertEquals("veh123", result.getVehicle().getId());
    }

    @Test
    void testGetFavoritesByUser() {
        when(favoriteRepository.findByUserId(1L)).thenReturn(List.of(favorite));

        List<Favorite> result = favoriteService.getFavoritesByUser(1L);

        assertEquals(1, result.size());
    }

    @Test
    void testGetFavorite_Found() {
        when(favoriteRepository.findByUserIdAndVehicleId(1L, "veh123"))
                .thenReturn(favorite);

        Optional<Favorite> result = favoriteService.getFavorite(1L, "veh123");

        assertTrue(result.isPresent());
    }

    @Test
    void testGetFavorite_NotFound() {
        when(favoriteRepository.findByUserIdAndVehicleId(1L, "veh999"))
                .thenReturn(null);

        Optional<Favorite> result = favoriteService.getFavorite(1L, "veh999");

        assertFalse(result.isPresent());
    }

    @Test
    void testRemoveFavorite() {
        when(favoriteRepository.findByUserIdAndVehicleId(1L, "veh123"))
                .thenReturn(favorite);

        doNothing().when(favoriteRepository).delete(favorite);

        favoriteService.removeFavorite(1L, "veh123");

        verify(favoriteRepository).delete(favorite);
    }
}
