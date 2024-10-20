package com.nnk.springboot.service;

import com.nnk.springboot.domain.CurvePoint;
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
        when(curvePointRepository.findAll()).thenReturn(Arrays.asList(mock(CurvePoint.class), mock(CurvePoint.class)));

        curvePointService.getAll();

        verify(curvePointRepository).findAll();
    }

    @Test
    void save_savesAndReturnsCurvePoint() {
        when(curvePointRepository.save(any(CurvePoint.class))).thenReturn(mock(CurvePoint.class));

        curvePointService.save(mock(CurvePoint.class));

        verify(curvePointRepository).save(any(CurvePoint.class));
    }

    @Test
    void getById_existingId_returnsCurvePoint() {
        when(curvePointRepository.findById(anyInt())).thenReturn(Optional.of(mock(CurvePoint.class)));

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
        CurvePoint existingCurvePoint = new CurvePoint();
        existingCurvePoint.setCurveId(1);
        CurvePoint newCurvePoint = new CurvePoint();
        newCurvePoint.setCurveId(2);

        when(curvePointRepository.findById(1)).thenReturn(Optional.of(existingCurvePoint));
        when(curvePointRepository.save(existingCurvePoint)).thenReturn(existingCurvePoint);

        curvePointService.update(1, newCurvePoint);

        verify(curvePointRepository).findById(1);
        verify(curvePointRepository).save(existingCurvePoint);
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