package com.nnk.springboot.service;

import com.nnk.springboot.domain.TradeEntity;
import com.nnk.springboot.repositories.TradeRepository;
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
class TradeServiceTest {

    @Mock
    private TradeRepository tradeRepository;

    @InjectMocks
    private TradeService tradeService;


    @Test
    void getAll_returnsAllTrades() {
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(mock(TradeEntity.class), mock(TradeEntity.class)));

        tradeService.getAll();

        verify(tradeRepository).findAll();
    }


    @Test
    void save_savesAndReturnsTrade() {
        when(tradeRepository.save(any(TradeEntity.class))).thenReturn(mock(TradeEntity.class));

        tradeService.save(mock(TradeEntity.class));

        verify(tradeRepository).save(any(TradeEntity.class));
    }

    @Test
    void getById_existingId_returnsTrade() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(mock(TradeEntity.class)));

        tradeService.getById(anyInt());

        verify(tradeRepository).findById(anyInt());
    }

    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> tradeService.getById(anyInt()));
        verify(tradeRepository).findById(anyInt());
    }


    @Test
    void update_existingId_updatesAndReturnsTrade() {
        TradeEntity existingTradeEntity = new TradeEntity();
        existingTradeEntity.setAccount("oldAccount");
        TradeEntity newTradeEntity = new TradeEntity();
        newTradeEntity.setAccount("newAccount");

        when(tradeRepository.findById(1)).thenReturn(Optional.of(existingTradeEntity));
        when(tradeRepository.save(existingTradeEntity)).thenReturn(existingTradeEntity);

        tradeService.update(1, newTradeEntity);

        verify(tradeRepository).findById(1);
        verify(tradeRepository).save(existingTradeEntity);
    }


    @Test
    void delete_existingId_deletesTrade() {
        when(tradeRepository.existsById(anyInt())).thenReturn(true);

        tradeService.delete(anyInt());

        verify(tradeRepository).existsById(anyInt());
        verify(tradeRepository).deleteById(anyInt());
    }

    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(tradeRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> tradeService.delete(anyInt()));
        verify(tradeRepository).existsById(anyInt());
    }
}