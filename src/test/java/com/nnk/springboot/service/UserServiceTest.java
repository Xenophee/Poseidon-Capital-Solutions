package com.nnk.springboot.service;

import com.nnk.springboot.domain.User;
import com.nnk.springboot.repositories.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;


    @Test
    void getAll_returnsAllUsers() {
        when(userRepository.findAll()).thenReturn(Arrays.asList(mock(User.class), mock(User.class)));

        userService.getAll();

        verify(userRepository).findAll();
    }

    @Test
    void save_savesAndReturnsUser() {
        when(userRepository.save(any(User.class))).thenReturn(mock(User.class));

        userService.save(mock(User.class));

        verify(userRepository).save(any(User.class));
    }

    @Test
    void getById_existingId_returnsUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mock(User.class)));

        userService.getById(anyInt());

        verify(userRepository).findById(anyInt());
    }

    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> userService.getById(anyInt()));
        verify(userRepository).findById(anyInt());
    }

    @Test
    void update_existingId_updatesAndReturnsUser() {
        User existingUser = new User();
        existingUser.setUsername("oldUsername");
        User newUser = new User();
        newUser.setUsername("newUsername");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUser));
        when(userRepository.save(existingUser)).thenReturn(existingUser);

        userService.update(1, newUser);

        verify(userRepository).findById(1);
        verify(userRepository).save(existingUser);
    }


    @Test
    void delete_existingId_deletesUser() {
        when(userRepository.existsById(anyInt())).thenReturn(true);

        userService.delete(anyInt());

        verify(userRepository).existsById(anyInt());
        verify(userRepository).deleteById(anyInt());
    }

    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(userRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> userService.delete(anyInt()));
        verify(userRepository).existsById(anyInt());
    }
}