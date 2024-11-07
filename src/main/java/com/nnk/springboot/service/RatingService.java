package com.nnk.springboot.service;


import com.nnk.springboot.domain.RatingEntity;
import com.nnk.springboot.repositories.RatingRepository;
import jakarta.persistence.EntityNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingService {

    public final Logger logger = LoggerFactory.getLogger(RatingService.class);

    private final RatingRepository ratingRepository;

    @Autowired
    public RatingService(RatingRepository ratingRepository) {
        this.ratingRepository = ratingRepository;
    }


    public List<RatingEntity> getAll() {
        return ratingRepository.findAll();
    }

    public RatingEntity save(RatingEntity ratingEntity) {
        return ratingRepository.save(ratingEntity);
    }


    public RatingEntity getById(int id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified rating not found : {}", id);
                    return new EntityNotFoundException("Specified rating not found");
                });
    }


    public RatingEntity update(int id, RatingEntity ratingEntity) {

        RatingEntity ratingEntityToUpdate = getById(id);
        ratingEntityToUpdate.setMoodysRating(ratingEntity.getMoodysRating());
        ratingEntityToUpdate.setSandPRating(ratingEntity.getSandPRating());
        ratingEntityToUpdate.setFitchRating(ratingEntity.getFitchRating());
        ratingEntityToUpdate.setOrderNumber(ratingEntity.getOrderNumber());

        if (!ratingEntity.equals(ratingEntityToUpdate)) return ratingEntityToUpdate;

        return ratingRepository.save(ratingEntityToUpdate);
    }


    public void delete(int id) {

        if (!ratingRepository.existsById(id)) {
            logger.warn("RatingEntity with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified rating not found");
        }

        ratingRepository.deleteById(id);
    }
}
