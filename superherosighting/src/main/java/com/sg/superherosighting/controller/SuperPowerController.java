/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
import com.sg.superherosighting.service.ServiceLayer;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
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
    private ServiceLayer service;

    @GetMapping("superPowers")
    public String getAllSuperPowers(Model model) {

        List<SuperPower> superPowers = service.getAllSuperPowers();
        List<Hero> heros = service.getAllHeros();

        model.addAttribute("superPowers", superPowers);
        model.addAttribute("heros", heros);
        model.addAttribute("errors", service.getSuperPowerViolations());

        return "superPowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperPower(HttpServletRequest request) {

        //superPower parms from client
        String name = request.getParameter("name");

        //heros id
        String heroId = request.getParameter("heroId");

        SuperPower superPower = new SuperPower();
        superPower.setName(name);

        Hero hero = null;

        if (!heroId.isEmpty()) {

            hero = service.getHeroById(Integer.parseInt(heroId));
        }

        //check if validation is empty then add super power
        if (service.validateSuperPower(superPower).isEmpty()) {

            if (!heroId.isEmpty()) {
                //set orgs
                superPower.setHero(hero);
            }
            //add super power
            superPower = service.addSuperPower(superPower);

            if (!heroId.isEmpty()) {
                service.insertSuperPowerHero(superPower);
            }
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
