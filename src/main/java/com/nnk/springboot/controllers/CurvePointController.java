package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.CurvePoint;
import com.nnk.springboot.service.CurvePointService;
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
public class CurvePointController {

    public final Logger logger = LoggerFactory.getLogger(CurvePointController.class);

    private final CurvePointService curveService;

    @Autowired
    public CurvePointController(CurvePointService curveService) {
        this.curveService = curveService;
    }

    @RequestMapping("/curvePoint/list")
    public String home(Model model) {
        model.addAttribute("curvePoints", curveService.getAll());
        return "curvePoint/list";
    }

    @GetMapping("/curvePoint/add")
    public String addCurveForm(CurvePoint bid) {
        return "curvePoint/add";
    }

    @PostMapping("/curvePoint/validate")
    public String validate(@Valid CurvePoint curvePoint, BindingResult result, Model model) {

        logger.info("Request to add CurvePoint: {}", curvePoint);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "curvePoint/add";
        }

        curveService.save(curvePoint);
        logger.info("New curve point added : {}", curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        CurvePoint curvePoint = curveService.getById(id);
        model.addAttribute("curvePoint", curvePoint);
        return "curvePoint/update";
    }


    @PostMapping("/curvePoint/update/{id}")
    public String updateCurve(@PathVariable("id") Integer id, @Valid CurvePoint curvePoint,
                             BindingResult result, Model model) {
        logger.info("Request to update curve point : {}", curvePoint);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "curvePoint/update";
        }

        curveService.update(id, curvePoint);
        logger.info("Curve point updated : {}", curvePoint);
        return "redirect:/curvePoint/list";
    }

    @GetMapping("/curvePoint/delete/{id}")
    public String deleteCurve(@PathVariable("id") Integer id, Model model) {
        logger.info("Request to delete curve point : {}", id);
        curveService.delete(id);
        logger.info("Curve point deleted : {}", id);
        return "redirect:/curvePoint/list";
    }
}
