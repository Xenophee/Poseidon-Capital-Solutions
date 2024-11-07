package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidListEntity;
import com.nnk.springboot.repositories.BidListRepository;
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
class BidListServiceTest {

    @Mock
    private BidListRepository bidListRepository;

    @InjectMocks
    private BidListService bidListService;



    @Test
    void getAll_returnsAllBids() {
        when(bidListRepository.findAll()).thenReturn(Arrays.asList(mock(BidListEntity.class), mock(BidListEntity.class)));

        bidListService.getAll();

        verify(bidListRepository).findAll();
    }


    @Test
    void save_savesAndReturnsBid() {
        when(bidListRepository.save(any(BidListEntity.class))).thenReturn(mock(BidListEntity.class));

        bidListService.save(mock(BidListEntity.class));

        verify(bidListRepository).save(any(BidListEntity.class));
    }


    @Test
    void getById_existingId_returnsBid() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.of(mock(BidListEntity.class)));

        bidListService.getById(anyInt());

        verify(bidListRepository).findById(anyInt());
    }


    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(bidListRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> bidListService.getById(anyInt()));
        verify(bidListRepository).findById(anyInt());
    }


    @Test
    void update_existingId_updatesAndReturnsBid() {
        BidListEntity existingBid = new BidListEntity();
        existingBid.setAccount("oldAccount");
        BidListEntity newBid = new BidListEntity();
        newBid.setAccount("newAccount");

        when(bidListRepository.findById(1)).thenReturn(Optional.of(existingBid));
        when(bidListRepository.save(existingBid)).thenReturn(existingBid);

        bidListService.update(1, newBid);

        verify(bidListRepository).findById(1);
        verify(bidListRepository).save(existingBid);
    }


    @Test
    void delete_existingId_deletesBid() {
        when(bidListRepository.existsById(anyInt())).thenReturn(true);

        bidListService.delete(anyInt());

        verify(bidListRepository).existsById(anyInt());
        verify(bidListRepository).deleteById(anyInt());
    }


    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(bidListRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> bidListService.delete(anyInt()));
        verify(bidListRepository).existsById(anyInt());
    }
}