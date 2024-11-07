package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePointEntity;
import com.nnk.springboot.repositories.CurvePointRepository;
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
class CurvePointServiceTest {

    @Mock
    private CurvePointRepository curvePointRepository;

    @InjectMocks
    private CurvePointService curvePointService;


    @Test
    void getAll_returnsAllCurvePoints() {
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(mock(CurvePointEntity.class), mock(CurvePointEntity.class)));

        curvePointService.getAll();

        verify(curvePointRepository).findAll();
    }

    @Test
    void save_savesAndReturnsCurvePoint() {
        when(curvePointRepository.save(any(CurvePointEntity.class))).thenReturn(mock(CurvePointEntity.class));

        curvePointService.save(mock(CurvePointEntity.class));

        verify(curvePointRepository).save(any(CurvePointEntity.class));
    }

    @Test
    void getById_existingId_returnsCurvePoint() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(mock(CurvePointEntity.class)));

        curvePointService.getById(anyInt());

        verify(curvePointRepository).findById(anyInt());
    }

    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> curvePointService.getById(anyInt()));
        verify(curvePointRepository).findById(anyInt());
    }

    @Test
    void update_existingId_updatesAndReturnsCurvePoint() {
        CurvePointEntity existingCurvePointEntity = new CurvePointEntity();
        existingCurvePointEntity.setCurveId(1);
        CurvePointEntity newCurvePointEntity = new CurvePointEntity();
        newCurvePointEntity.setCurveId(2);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(existingCurvePointEntity));
        when(curvePointRepository.save(existingCurvePointEntity)).thenReturn(existingCurvePointEntity);

        curvePointService.update(1, newCurvePointEntity);

        verify(curvePointRepository).findById(1);
        verify(curvePointRepository).save(existingCurvePointEntity);
    }


    @Test
    void delete_existingId_deletesCurvePoint() {
        when(curvePointRepository.existsById(anyInt())).thenReturn(true);

        curvePointService.delete(anyInt());

        verify(curvePointRepository).existsById(anyInt());
        verify(curvePointRepository).deleteById(anyInt());
    }

    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(curvePointRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> curvePointService.delete(anyInt()));
        verify(curvePointRepository).existsById(anyInt());
    }
}