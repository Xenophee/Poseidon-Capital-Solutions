package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.TradeEntity;
import com.nnk.springboot.service.TradeService;
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
public class TradeController {

    public final Logger logger = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("tradeEntity", new TradeEntity());
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid TradeEntity tradeEntity, BindingResult result, Model model) {
        logger.info("Request to add TradeEntity: {}", tradeEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "trade/add";
        }

        TradeEntity savedTradeEntity = tradeService.save(tradeEntity);
        logger.info("New tradeEntity added : {}", savedTradeEntity);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        TradeEntity tradeEntity = tradeService.getById(id);
        model.addAttribute("tradeEntity", tradeEntity);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid TradeEntity tradeEntity,
                             BindingResult result, Model model) {
        logger.info("Request to update TradeEntity: {}", tradeEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "trade/update";
        }

        tradeService.update(id, tradeEntity);
        logger.info("TradeEntity updated : {}", tradeEntity);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete TradeEntity: {}", id);
        tradeService.delete(id);
        logger.info("TradeEntity deleted : {}", id);
        return "redirect:/trade/list";
    }
}
