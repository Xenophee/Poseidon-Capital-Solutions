package com.nnk.springboot.service;

import com.nnk.springboot.domain.Trade;
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
        when(tradeRepository.findAll()).thenReturn(Arrays.asList(mock(Trade.class), mock(Trade.class)));

        tradeService.getAll();

        verify(tradeRepository).findAll();
    }


    @Test
    void save_savesAndReturnsTrade() {
        when(tradeRepository.save(any(Trade.class))).thenReturn(mock(Trade.class));

        tradeService.save(mock(Trade.class));

        verify(tradeRepository).save(any(Trade.class));
    }

    @Test
    void getById_existingId_returnsTrade() {
        when(tradeRepository.findById(anyInt())).thenReturn(Optional.of(mock(Trade.class)));

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
        Trade existingTrade = new Trade();
        existingTrade.setAccount("oldAccount");
        Trade newTrade = new Trade();
        newTrade.setAccount("newAccount");

        when(tradeRepository.findById(1)).thenReturn(Optional.of(existingTrade));
        when(tradeRepository.save(existingTrade)).thenReturn(existingTrade);

        tradeService.update(1, newTrade);

        verify(tradeRepository).findById(1);
        verify(tradeRepository).save(existingTrade);
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