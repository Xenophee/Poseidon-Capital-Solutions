package com.nnk.springboot.service;

import com.nnk.springboot.domain.Rating;
import com.nnk.springboot.repositories.RatingRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
public class RatingTests {

	@Autowired
	private RatingRepository ratingRepository;

	@Test
	public void ratingTest() {
		Rating rating = new Rating("Moodys Rating", "Sand PRating", "Fitch Rating", 10);

		// Save
		rating = ratingRepository.save(rating);
		assertThat(rating.getId()).isNotNull();
		assertThat(rating.getOrderNumber()).isEqualTo(10);

		// Update
		rating.setOrderNumber(20);
		rating = ratingRepository.save(rating);
		assertThat(rating.getOrderNumber()).isEqualTo(20);

		// Find
		List<Rating> listResult = ratingRepository.findAll();
		assertThat(listResult).isNotEmpty();

		// Delete
		Integer id = rating.getId();
		ratingRepository.delete(rating);
		Optional<Rating> ratingList = ratingRepository.findById(id);
		assertThat(ratingList).isNotPresent();
	}
}