package com.MotorSport.LegendaryMotorSport.serviceTest;


import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import java.time.LocalDateTime;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import com.MotorSport.LegendaryMotorSport.model.User;
import com.MotorSport.LegendaryMotorSport.repository.UserRepository;
import com.MotorSport.LegendaryMotorSport.service.UserService;

@SpringBootTest
public class UserServiceTest {

    @Autowired
    private UserService userService;

    @MockitoBean
    private UserRepository userRepository;

    @Test
    public void testSaveUser() {
        User user = new User(1L, "benja", "benja@example.com", "1234", "USER", LocalDateTime.now());

        when(userRepository.save(user)).thenReturn(user);

        User saved = userService.saveUser(user);

        assertNotNull(saved);
        assertEquals("benja", saved.getUsername());
        assertEquals("benja@example.com", saved.getEmail());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testFindByUsername() {
        User user = new User(1L, "benja", "benja@example.com", "1234", "USER", LocalDateTime.now());

        when(userRepository.findByUsername("benja")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByUsername("benja");

        assertTrue(result.isPresent());
        assertEquals("benja@example.com", result.get().getEmail());
        verify(userRepository, times(1)).findByUsername("benja");
    }

    @Test
    public void testFindByEmail() {
        User user = new User(1L, "benja", "benja@example.com", "1234", "USER", LocalDateTime.now());

        when(userRepository.findByEmail("benja@example.com")).thenReturn(Optional.of(user));

        Optional<User> result = userService.findByEmail("benja@example.com");

        assertTrue(result.isPresent());
        assertEquals("benja", result.get().getUsername());
        verify(userRepository, times(1)).findByEmail("benja@example.com");
    }

    @Test
    public void testFindById() {
        User user = new User(1L, "benja", "benja@example.com", "1234", "USER", LocalDateTime.now());

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        Optional<User> result = userService.findById(1L);

        assertTrue(result.isPresent());
        assertEquals("benja", result.get().getUsername());
        verify(userRepository, times(1)).findById(1L);
    }

    @Test
    public void testDeleteUser() {
        Long id = 1L;
        doNothing().when(userRepository).deleteById(id);

        userService.deleteUser(id);

        verify(userRepository, times(1)).deleteById(id);
    }
}

