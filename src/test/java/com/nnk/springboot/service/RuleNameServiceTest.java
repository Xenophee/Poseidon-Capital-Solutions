package com.nnk.springboot.service;

import com.nnk.springboot.domain.RuleNameEntity;
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
        when(ruleNameRepository.findAll()).thenReturn(Arrays.asList(mock(RuleNameEntity.class), mock(RuleNameEntity.class)));

        ruleNameService.getAll();

        verify(ruleNameRepository).findAll();
    }


    @Test
    void save_savesAndReturnsRuleName() {
        when(ruleNameRepository.save(any(RuleNameEntity.class))).thenReturn(mock(RuleNameEntity.class));

        ruleNameService.save(mock(RuleNameEntity.class));

        verify(ruleNameRepository).save(any(RuleNameEntity.class));
    }


    @Test
    void getById_existingId_returnsRuleName() {
        when(ruleNameRepository.findById(anyInt())).thenReturn(Optional.of(mock(RuleNameEntity.class)));

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
        RuleNameEntity existingRuleNameEntity = new RuleNameEntity();
        existingRuleNameEntity.setName("oldName");
        RuleNameEntity newRuleNameEntity = new RuleNameEntity();
        newRuleNameEntity.setName("newName");

        when(ruleNameRepository.findById(1)).thenReturn(Optional.of(existingRuleNameEntity));
        when(ruleNameRepository.save(existingRuleNameEntity)).thenReturn(existingRuleNameEntity);

        ruleNameService.update(1, newRuleNameEntity);

        verify(ruleNameRepository).findById(1);
        verify(ruleNameRepository).save(existingRuleNameEntity);
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