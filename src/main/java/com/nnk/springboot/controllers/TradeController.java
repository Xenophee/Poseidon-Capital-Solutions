package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.Trade;
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



@Controller
public class TradeController {

    public final Logger logger = LoggerFactory.getLogger(TradeController.class);

    private final TradeService tradeService;

    @Autowired
    public TradeController(TradeService tradeService) {
        this.tradeService = tradeService;
    }

    @RequestMapping("/trade/list")
    public String home(Model model) {
        model.addAttribute("trades", tradeService.getAll());
        return "trade/list";
    }

    @GetMapping("/trade/add")
    public String addUser(Trade bid) {
        return "trade/add";
    }

    @PostMapping("/trade/validate")
    public String validate(@Valid Trade trade, BindingResult result, Model model) {
        logger.info("Request to add Trade: {}", trade);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "trade/add";
        }

        Trade savedTrade = tradeService.save(trade);
        logger.info("New trade added : {}", savedTrade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        Trade trade = tradeService.getById(id);
        model.addAttribute("trade", trade);
        return "trade/update";
    }

    @PostMapping("/trade/update/{id}")
    public String updateTrade(@PathVariable("id") Integer id, @Valid Trade trade,
                             BindingResult result, Model model) {
        logger.info("Request to update Trade: {}", trade);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "trade/update";
        }

        tradeService.update(id, trade);
        logger.info("Trade updated : {}", trade);
        return "redirect:/trade/list";
    }

    @GetMapping("/trade/delete/{id}")
    public String deleteTrade(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete Trade: {}", id);
        tradeService.delete(id);
        logger.info("Trade deleted : {}", id);
        return "redirect:/trade/list";
    }
}
