package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.RuleNameEntity;
import com.nnk.springboot.service.RuleNameService;
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
public class RuleNameController {

    public final Logger logger = LoggerFactory.getLogger(RuleNameController.class);

    private final RuleNameService ruleNameService;

    @Autowired
    public RuleNameController(RuleNameService ruleNameService) {
        this.ruleNameService = ruleNameService;
    }

    @RequestMapping("/ruleName/list")
    public String home(Model model, Principal principal) {
        model.addAttribute("username", principal.getName());
        model.addAttribute("ruleNames", ruleNameService.getAll());
        return "ruleName/list";
    }

    @GetMapping("/ruleName/add")
    public String addRuleForm(Model model) {
        model.addAttribute("ruleNameEntity", new RuleNameEntity());
        return "ruleName/add";
    }

    @PostMapping("/ruleName/validate")
    public String validate(@Valid RuleNameEntity ruleNameEntity, BindingResult result, Model model) {

        logger.info("Request to add RuleNameEntity : {}", ruleNameEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "ruleName/add";
        }

        ruleNameService.save(ruleNameEntity);
        logger.info("New ruleNameEntity added : {}", ruleNameEntity);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        RuleNameEntity ruleNameEntity = ruleNameService.getById(id);
        model.addAttribute("ruleNameEntity", ruleNameEntity);
        return "ruleName/update";
    }

    @PostMapping("/ruleName/update/{id}")
    public String updateRuleName(@PathVariable("id") Integer id, @Valid RuleNameEntity ruleNameEntity,
                             BindingResult result, Model model) {

        logger.info("Request to update RuleNameEntity : {}", ruleNameEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "ruleName/update";
        }

        ruleNameService.update(id, ruleNameEntity);
        logger.info("RuleNameEntity updated : {}", id);
        return "redirect:/ruleName/list";
    }

    @GetMapping("/ruleName/delete/{id}")
    public String deleteRuleName(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete ruleName : {}", id);
        ruleNameService.delete(id);
        logger.info("RuleNameEntity deleted : {}", id);
        return "redirect:/ruleName/list";
    }
}
