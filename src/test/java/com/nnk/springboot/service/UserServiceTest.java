package com.nnk.springboot.service;

import com.nnk.springboot.domain.UserEntity;
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
        when(userRepository.findAll()).thenReturn(Arrays.asList(mock(UserEntity.class), mock(UserEntity.class)));

        userService.getAll();

        verify(userRepository).findAll();
    }

    @Test
    void save_savesAndReturnsUser() {
        when(userRepository.save(any(UserEntity.class))).thenReturn(mock(UserEntity.class));

        userService.save(mock(UserEntity.class));

        verify(userRepository).save(any(UserEntity.class));
    }

    @Test
    void getById_existingId_returnsUser() {
        when(userRepository.findById(anyInt())).thenReturn(Optional.of(mock(UserEntity.class)));

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
        UserEntity existingUserEntity = new UserEntity();
        existingUserEntity.setUsername("oldUsername");
        UserEntity newUserEntity = new UserEntity();
        newUserEntity.setUsername("newUsername");

        when(userRepository.findById(1)).thenReturn(Optional.of(existingUserEntity));
        when(userRepository.save(existingUserEntity)).thenReturn(existingUserEntity);

        userService.update(1, newUserEntity);

        verify(userRepository).findById(1);
        verify(userRepository).save(existingUserEntity);
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