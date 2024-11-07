package com.nnk.springboot.service;


import com.nnk.springboot.domain.TradeEntity;
import com.nnk.springboot.repositories.TradeRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TradeService {

    public final Logger logger = LoggerFactory.getLogger(TradeService.class);

    private final TradeRepository tradeRepository;

    @Autowired
    public TradeService(TradeRepository tradeRepository) {
        this.tradeRepository = tradeRepository;
    }


    public List<TradeEntity> getAll() {
        return tradeRepository.findAll();
    }

    public TradeEntity save(TradeEntity tradeEntity) {
        return tradeRepository.save(tradeEntity);
    }

    public TradeEntity getById(int id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified trade not found : {}", id);
                    return new EntityNotFoundException("Specified trade not found");
                });
    }

    public TradeEntity update(int id, TradeEntity tradeEntity) {

        TradeEntity tradeEntityToUpdate = getById(id);
        tradeEntityToUpdate.setAccount(tradeEntity.getAccount());
        tradeEntityToUpdate.setType(tradeEntity.getType());
        tradeEntityToUpdate.setBuyQuantity(tradeEntity.getBuyQuantity());

        if (!tradeEntity.equals(tradeEntityToUpdate)) return tradeEntityToUpdate;

        return tradeRepository.save(tradeEntityToUpdate);
    }

    public void delete(int id) {

        if (!tradeRepository.existsById(id)) {
            logger.warn("TradeEntity with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified trade not found");
        }

        tradeRepository.deleteById(id);
    }
}
