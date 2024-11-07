package com.nnk.springboot.service;

import com.nnk.springboot.domain.RatingEntity;
import com.nnk.springboot.repositories.RatingRepository;
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
class RatingServiceTest {

    @Mock
    private RatingRepository ratingRepository;

    @InjectMocks
    private RatingService ratingService;


    @Test
    void getAll_returnsAllRatings() {
        when(ratingRepository.findAll()).thenReturn(Arrays.asList(mock(RatingEntity.class), mock(RatingEntity.class)));

        ratingService.getAll();

        verify(ratingRepository).findAll();
    }

    @Test
    void save_savesAndReturnsRating() {
        when(ratingRepository.save(any(RatingEntity.class))).thenReturn(mock(RatingEntity.class));

        ratingService.save(mock(RatingEntity.class));

        verify(ratingRepository).save(any(RatingEntity.class));
    }

    @Test
    void getById_existingId_returnsRating() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.of(mock(RatingEntity.class)));

        ratingService.getById(anyInt());

        verify(ratingRepository).findById(anyInt());
    }

    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(ratingRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ratingService.getById(anyInt()));
        verify(ratingRepository).findById(anyInt());
    }

    @Test
    void update_existingId_updatesAndReturnsRating() {
        RatingEntity existingRatingEntity = new RatingEntity();
        existingRatingEntity.setMoodysRating("oldMoodys");
        RatingEntity newRatingEntity = new RatingEntity();
        newRatingEntity.setMoodysRating("newMoodys");

        when(ratingRepository.findById(1)).thenReturn(Optional.of(existingRatingEntity));
        when(ratingRepository.save(existingRatingEntity)).thenReturn(existingRatingEntity);

        ratingService.update(1, newRatingEntity);

        verify(ratingRepository).findById(1);
        verify(ratingRepository).save(existingRatingEntity);
    }


    @Test
    void delete_existingId_deletesRating() {
        when(ratingRepository.existsById(anyInt())).thenReturn(true);

        ratingService.delete(anyInt());

        verify(ratingRepository).existsById(anyInt());
        verify(ratingRepository).deleteById(anyInt());
    }

    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(ratingRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> ratingService.delete(anyInt()));
        verify(ratingRepository).existsById(anyInt());
    }
}