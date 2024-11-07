package com.nnk.springboot.controllers;

import com.nnk.springboot.domain.UserEntity;
import com.nnk.springboot.service.UserService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
public class UserController {

    private final Logger logger = LoggerFactory.getLogger(RatingController.class);

    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @RequestMapping("/user/list")
    public String home(Model model) {
        model.addAttribute("users", userService.getAll());
        return "user/list";
    }

    @GetMapping("/user/add")
    public String addUser(Model model) {
        model.addAttribute("userEntity", new UserEntity());
        return "user/add";
    }

    @PostMapping("/user/validate")
    public String validate(@Valid UserEntity userEntity, BindingResult result, Model model) {

        logger.info("Request to add UserEntity: {}", userEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "user/add";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userService.save(userEntity);

        logger.info("New userEntity added : {}", userEntity);
        return "redirect:/user/list";
    }

    @GetMapping("/user/update/{id}")
    public String showUpdateForm(@PathVariable("id") Integer id, Model model) {
        UserEntity userEntity = userService.getById(id);
        userEntity.setPassword("");
        model.addAttribute("userEntity", userEntity);
        return "user/update";
    }

    @PostMapping("/user/update/{id}")
    public String updateUser(@PathVariable("id") Integer id, @Valid UserEntity userEntity,
                             BindingResult result, Model model) {

        logger.info("Request to update UserEntity: {}", userEntity);

        if (result.hasErrors()) {
            logger.warn("Invalid data for registration : {}", result.getAllErrors());
            return "user/update";
        }

        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        userEntity.setPassword(encoder.encode(userEntity.getPassword()));
        userService.save(userEntity);

        logger.info("UserEntity updated : {}", id);
        return "redirect:/user/list";
    }

    @GetMapping("/user/delete/{id}")
    public String deleteUser(@PathVariable("id") Integer id, Model model) {
        userService.delete(id);
        return "redirect:/user/list";
    }
}
