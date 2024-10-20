package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleName;
import com.nnk.springboot.repositories.RuleNameRepository;
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
class RuleNameServiceTest {

    @Mock
    private RuleNameRepository ruleNameRepository;

    @InjectMocks
    private RuleNameService ruleNameService;


    @Test
    void getAll_returnsAllRuleNames() {
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(mock(RuleName.class), mock(RuleName.class)));

        ruleNameService.getAll();

        verify(ruleNameRepository).findAll();
    }


    @Test
    void save_savesAndReturnsRuleName() {
        when(ruleNameRepository.save(any(RuleName.class))).thenReturn(mock(RuleName.class));

        ruleNameService.save(mock(RuleName.class));

        verify(ruleNameRepository).save(any(RuleName.class));
    }


    @Test
    void getById_existingId_returnsRuleName() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(mock(RuleName.class)));

        ruleNameService.getById(anyInt());

        verify(ruleNameRepository).findById(anyInt());
    }


    @Test
    void getById_nonExistingId_throwsEntityNotFoundException() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> ruleNameService.getById(anyInt()));
        verify(ruleNameRepository).findById(anyInt());
    }


    @Test
    void update_existingId_updatesAndReturnsRuleName() {
        RuleName existingRuleName = new RuleName();
        existingRuleName.setName("oldName");
        RuleName newRuleName = new RuleName();
        newRuleName.setName("newName");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(existingRuleName));
        when(ruleNameRepository.save(existingRuleName)).thenReturn(existingRuleName);

        ruleNameService.update(1, newRuleName);

        verify(ruleNameRepository).findById(1);
        verify(ruleNameRepository).save(existingRuleName);
    }


    @Test
    void delete_existingId_deletesRuleName() {
        when(ruleNameRepository.existsById(anyInt())).thenReturn(true);

        ruleNameService.delete(anyInt());

        verify(ruleNameRepository).existsById(anyInt());
        verify(ruleNameRepository).deleteById(anyInt());
    }


    @Test
    void delete_nonExistingId_throwsEntityNotFoundException() {
        when(ruleNameRepository.existsById(anyInt())).thenReturn(false);

        assertThrows(EntityNotFoundException.class, () -> ruleNameService.delete(anyInt()));
        verify(ruleNameRepository).existsById(anyInt());
    }
}