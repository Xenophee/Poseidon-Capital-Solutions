package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RatingEntity;
import com.nnk.springboot.service.RatingService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;


@Controller
public class RatingController {

    private final Logger logger = LoggerFactory.getLogger(RatingController.class);

    private final RatingService ratingService;

    @Autowired
    public RatingController(RatingService ratingService) {
        this.ratingService = ratingService;
    }

    @RequestMapping("/rating/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("ratings", ratingService.getAll());
        return "rating/list";
    }

    @GetMapping("/rating/add")
    public String addRatingForm(Model model) {
        model.addAttribute("ratingEntity", new RatingEntity());
        return "rating/add";
    }

    @PostMapping("/rating/validate")
    public String validate(@Valid RatingEntity ratingEntity, BindingResult result, Model model) {

        logger.info("Request to add RatingEntity: {}", ratingEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "rating/add";
        }

        ratingService.save(ratingEntity);
        logger.info("New ratingEntity added : {}", ratingEntity);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RatingEntity ratingEntity = ratingService.getById(id);
        model.addAttribute("ratingEntity", ratingEntity);
        return "rating/update";
    }

    @PostMapping("/rating/update/{id}")
    public String updateRating(@PathVariable("id") Integer id, @Valid RatingEntity ratingEntity,
                             BindingResult result, Model model) {

        logger.info("Request to update RatingEntity: {}", ratingEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "rating/update";
        }

        ratingService.update(id, ratingEntity);
        logger.info("RatingEntity updated : {}", id);
        return "redirect:/rating/list";
    }

    @GetMapping("/rating/delete/{id}")
    public String deleteRating(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete RatingEntity with id: {}", id);
        ratingService.delete(id);
        logger.info("RatingEntity deleted : {}", id);
        return "redirect:/rating/list";
    }
}
