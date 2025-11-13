package com.MotorSport.LegendaryMotorSport.serviceTest;

import com.MotorSport.LegendaryMotorSport.model.Favorite;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.model.vehicleModel.Vehicle;
import com.MotorSport.LegendaryMotorSport.repository.FavoriteRepository;
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

    @InjectMocks
    private FavoriteService favoriteService;

    private Favorite favorite;

    @BeforeEach
    void setUp() {
        User user = new User();
        user.setId(1L);

        Vehicle vehicle = new Vehicle();
        vehicle.setId("veh123");

        favorite = new Favorite(null, user, vehicle, LocalDateTime.now());
    }

    @Test
    void testAddFavorite() {
        when(favoriteRepository.save(any(Favorite.class))).thenReturn(favorite);

        Favorite result = favoriteService.addFavorite(favorite);

        assertNotNull(result);
        assertEquals("veh123", result.getVehicle().getId());
        verify(favoriteRepository).save(any(Favorite.class));
    }

    @Test
    void testGetFavoritesByUser() {
        when(favoriteRepository.findByUserId(1L)).thenReturn(List.of(favorite));

        List<Favorite> result = favoriteService.getFavoritesByUser(1L);

        assertEquals(1, result.size());
        verify(favoriteRepository).findByUserId(1L);
    }

    @Test
    void testGetFavorite_Found() {
        when(favoriteRepository.findByUserIdAndVehicleId(1L, "veh123")).thenReturn(Optional.of(favorite));

        Optional<Favorite> result = favoriteService.getFavorite(1L, "veh123");

        assertTrue(result.isPresent());
        verify(favoriteRepository).findByUserIdAndVehicleId(1L, "veh123");
    }

    @Test
    void testGetFavorite_NotFound() {
        when(favoriteRepository.findByUserIdAndVehicleId(1L, "veh999")).thenReturn(Optional.empty());

        Optional<Favorite> result = favoriteService.getFavorite(1L, "veh999");

        assertFalse(result.isPresent());
    }

    @Test
    void testRemoveFavorite() {
        doNothing().when(favoriteRepository).deleteByUserIdAndVehicleId(1L, "veh123");

        favoriteService.removeFavorite(1L, "veh123");

        verify(favoriteRepository).deleteByUserIdAndVehicleId(1L, "veh123");
    }
}
