package com.nnk.springboot.service;


import com.nnk.springboot.domain.RuleNameEntity;
import com.nnk.springboot.repositories.RuleNameRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RuleNameService {

    public final Logger logger = LoggerFactory.getLogger(RuleNameService.class);

    private final RuleNameRepository ruleNameRepository;

    @Autowired
    public RuleNameService(RuleNameRepository ruleNameRepository) {
        this.ruleNameRepository = ruleNameRepository;
    }


    public List<RuleNameEntity> getAll() {
        return ruleNameRepository.findAll();
    }

    public RuleNameEntity save(RuleNameEntity ruleNameEntity) {
        return ruleNameRepository.save(ruleNameEntity);
    }

    public RuleNameEntity getById(int id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified rule name not found : {}", id);
                    return new EntityNotFoundException("Specified rule name not found");
                });
    }

    public RuleNameEntity update(int id, RuleNameEntity ruleNameEntity) {

        RuleNameEntity ruleNameEntityToUpdate = getById(id);
        ruleNameEntityToUpdate.setName(ruleNameEntity.getName());
        ruleNameEntityToUpdate.setDescription(ruleNameEntity.getDescription());
        ruleNameEntityToUpdate.setJson(ruleNameEntity.getJson());
        ruleNameEntityToUpdate.setTemplate(ruleNameEntity.getTemplate());
        ruleNameEntityToUpdate.setSqlStr(ruleNameEntity.getSqlStr());
        ruleNameEntityToUpdate.setSqlPart(ruleNameEntity.getSqlPart());

        if (!ruleNameEntity.equals(ruleNameEntityToUpdate)) return ruleNameEntityToUpdate;

        return ruleNameRepository.save(ruleNameEntityToUpdate);
    }

    public void delete(int id) {

        if (!ruleNameRepository.existsById(id)) {
            logger.warn("Rule name with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified rule name not found");
        }

        ruleNameRepository.deleteById(id);
    }
}
