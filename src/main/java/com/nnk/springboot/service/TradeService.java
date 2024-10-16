package com.nnk.springboot.service;


import com.nnk.springboot.domain.Trade;
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


    public List<Trade> getAll() {
        return tradeRepository.findAll();
    }

    public Trade save(Trade trade) {
        return tradeRepository.save(trade);
    }

    public Trade getById(int id) {
        return tradeRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified trade not found : {}", id);
                    return new EntityNotFoundException("Specified trade not found");
                });
    }

    public Trade update(int id, Trade trade) {

        Trade tradeToUpdate = getById(id);
        tradeToUpdate.setAccount(trade.getAccount());
        tradeToUpdate.setType(trade.getType());
        tradeToUpdate.setBuyQuantity(trade.getBuyQuantity());

        if (!trade.equals(tradeToUpdate)) return tradeToUpdate;

        return tradeRepository.save(tradeToUpdate);
    }

    public void delete(int id) {

        if (!tradeRepository.existsById(id)) {
            logger.warn("Trade with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified trade not found");
        }

        tradeRepository.deleteById(id);
    }
}
