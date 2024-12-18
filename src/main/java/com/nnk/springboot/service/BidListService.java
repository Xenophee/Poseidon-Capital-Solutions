package com.nnk.springboot.service;

import com.nnk.springboot.domain.BidListEntity;
import com.nnk.springboot.repositories.BidListRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;


@Service
public class BidListService {

    public final Logger logger = LoggerFactory.getLogger(BidListService.class);

    private final BidListRepository bidListRepository;

    @Autowired
    public BidListService(BidListRepository bidListRepository) {
        this.bidListRepository = bidListRepository;
    }


    public List<BidListEntity> getAll() {
        return bidListRepository.findAll();
    }

    public BidListEntity save(BidListEntity bid) {
        return bidListRepository.save(bid);
    }

    public BidListEntity getById(int id) {
        return bidListRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified bid not found : {}", id);
                    return new EntityNotFoundException("Specified bid not found");
                });
    }

    public BidListEntity update(int id, BidListEntity bid) {

        BidListEntity bidToUpdate = getById(id);
        bidToUpdate.setAccount(bid.getAccount());
        bidToUpdate.setType(bid.getType());
        bidToUpdate.setBidQuantity(bid.getBidQuantity());

        if (!bid.equals(bidToUpdate)) return bidToUpdate;

        return bidListRepository.save(bidToUpdate);
    }

    public void delete(int id) {

        if (!bidListRepository.existsById(id)) {
            logger.warn("Bid with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified bid not found");
        }
        bidListRepository.deleteById(id);
    }
}
