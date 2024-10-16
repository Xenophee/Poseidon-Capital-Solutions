package com.nnk.springboot.service;


import com.nnk.springboot.domain.RuleName;
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


    public List<RuleName> getAll() {
        return ruleNameRepository.findAll();
    }

    public RuleName save(RuleName ruleName) {
        return ruleNameRepository.save(ruleName);
    }

    public RuleName getById(int id) {
        return ruleNameRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified rule name not found : {}", id);
                    return new EntityNotFoundException("Specified rule name not found");
                });
    }

    public RuleName update(int id, RuleName ruleName) {

        RuleName ruleNameToUpdate = getById(id);
        ruleNameToUpdate.setName(ruleName.getName());
        ruleNameToUpdate.setDescription(ruleName.getDescription());
        ruleNameToUpdate.setJson(ruleName.getJson());
        ruleNameToUpdate.setTemplate(ruleName.getTemplate());
        ruleNameToUpdate.setSqlStr(ruleName.getSqlStr());
        ruleNameToUpdate.setSqlPart(ruleName.getSqlPart());

        if (!ruleName.equals(ruleNameToUpdate)) return ruleNameToUpdate;

        return ruleNameRepository.save(ruleNameToUpdate);
    }

    public void delete(int id) {

        if (!ruleNameRepository.existsById(id)) {
            logger.warn("Rule name with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified rule name not found");
        }

        ruleNameRepository.deleteById(id);
    }
}
