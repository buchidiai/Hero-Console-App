/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
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
public class HeroController {

    @Autowired
    ServiceLayer service;

    Set<ConstraintViolation<Hero>> heroViolations = new HashSet<>();
    Set<ConstraintViolation<SuperPower>> superPowerViolations = new HashSet<>();

    @GetMapping("heros")
    public String getAllHeros(Model model) {

        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);
        model.addAttribute("errors", heroViolations);

        return "heros";
    }

    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {

        //hero parms from client
        String name = request.getParameter("name");
        String description = request.getParameter("description");

        SuperPower superPower = new SuperPower();
        //super power params
        String super_Power = request.getParameter("superPower");
        superPower.setSuperPower(super_Power);

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        //check if valid
        superPowerViolations = validate.validate(superPower);

        //check if empty then add
        if (superPowerViolations.isEmpty()) {
            //add super power
            superPower = service.addSuperPower(superPower);
        }

        Hero hero = new Hero();
        hero.setName(name);
        hero.setDescription(description);
        hero.setSuperPower(String.valueOf(superPower.getId()));

        //check if valid
        heroViolations = validate.validate(hero);

        System.out.println("heroViolations size " + heroViolations.size());

        //check if empty then add
        if (heroViolations.isEmpty()) {
            //add hero
            service.addHero(hero);
        }

        return "redirect:/heros";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {
        Hero hero = service.getHeroById(id);
        model.addAttribute("hero", hero);
        return "editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result) {

        if (result.hasErrors()) {
            return "editHero";
        }

//        Hero hero = service.getHeroById(id);
//        hero.setName(request.getParameter("name"));
//        hero.setDescription(request.getParameter("description"));
        service.updateHero(hero);
        return "redirect:/heros";
    }

    @GetMapping("deleteHeroConfirm")
    public String deleteHeroConfirm(Integer id, Model model) {

        model.addAttribute("heroId", id);

        return "deleteHeroConfirm";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id) {

        service.deleteHeroById(id);

        return "redirect:/heros";
    }
}
