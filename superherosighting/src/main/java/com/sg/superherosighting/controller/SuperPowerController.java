/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.SuperPower;
import com.sg.superherosighting.service.ServiceLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author louie
 */
@Controller
public class SuperPowerController {

    @Autowired
    ServiceLayer service;

    Set<ConstraintViolation<SuperPower>> superPowerViolations = new HashSet<>();

    @GetMapping("superPowers")
    public String getAllSuperPowers(Model model) {

        List<SuperPower> superPowers = service.getAllSuperPowers();

        model.addAttribute("superPowers", superPowers);
        model.addAttribute("errors", superPowerViolations);

        return "superPowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperPower(HttpServletRequest request) {

        //superPower parms from client
        String name = request.getParameter("name");

        SuperPower superPower = new SuperPower();
        superPower.setName(name);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        //check if valid
        superPowerViolations = validate.validate(superPower);

        //check if empty then add
        if (superPowerViolations.isEmpty()) {
            //add super power
            superPower = service.addSuperPower(superPower);
        }

        return "redirect:/superPowers";
    }

    @GetMapping("editSuperPower")
    public String editSuperPower(Integer id, Model model) {

        SuperPower superPower = service.getSuperPowerById(id);

        model.addAttribute("superPower", superPower);
        return "editSuperPower";
    }

    @PostMapping("editSuperPower")
    public String performEditSuperPower(@Valid SuperPower superPower, BindingResult result) {

        if (result.hasErrors()) {
            return "editSuperPower";
        }

        service.updateSuperPower(superPower);
        return "redirect:/superPowers";
    }

    @GetMapping("deleteSuperPowerConfirm")
    public String deleteSuperPowerConfirm(Integer id, Model model) {

        model.addAttribute("superPowerId", id);

        return "deleteSuperPowerConfirm";
    }

    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(Integer id) {

        service.deleteSuperPowerById(id);

        return "redirect:/superPowers";
    }
}
