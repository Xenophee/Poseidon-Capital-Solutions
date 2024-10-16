package com.nnk.springboot.service;


import com.nnk.springboot.domain.Rating;
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


    public List<Rating> getAll() {
        return ratingRepository.findAll();
    }

    public Rating save(Rating rating) {
        return ratingRepository.save(rating);
    }


    public Rating getById(int id) {
        return ratingRepository.findById(id)
                .orElseThrow(() -> {
                    logger.warn("Specified rating not found : {}", id);
                    return new EntityNotFoundException("Specified rating not found");
                });
    }


    public Rating update(int id, Rating rating) {

        Rating ratingToUpdate = getById(id);
        ratingToUpdate.setMoodysRating(rating.getMoodysRating());
        ratingToUpdate.setSandPRating(rating.getSandPRating());
        ratingToUpdate.setFitchRating(rating.getFitchRating());
        ratingToUpdate.setOrderNumber(rating.getOrderNumber());

        if (!rating.equals(ratingToUpdate)) return ratingToUpdate;

        return ratingRepository.save(ratingToUpdate);
    }


    public void delete(int id) {

        if (!ratingRepository.existsById(id)) {
            logger.warn("Rating with id {} not found for deletion", id);
            throw new EntityNotFoundException("Specified rating not found");
        }

        ratingRepository.deleteById(id);
    }
}
