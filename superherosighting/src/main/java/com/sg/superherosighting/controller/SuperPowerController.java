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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author louie
 */
@Controller
public class SuperPowerController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("superPower")
    public String getSuperPowerPage(Model model) {

        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);
        model.addAttribute("errors", service.getSuperPowerViolations());

        return "/superPower/superPower";
    }

    @GetMapping("superPowers")
    public String getAllSuperPowers(Model model) {

        List<SuperPower> superPowers = service.getAllSuperPowers();

        model.addAttribute("superPowers", superPowers);
        model.addAttribute("errors", service.getSuperPowerViolations());

        return "/superPower/listSuperPowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperPower(HttpServletRequest request) {

        //superPower parms from client
        String name = request.getParameter("name");

        //heros id
        String heroId = request.getParameter("heroId");

        SuperPower superPower = new SuperPower();

        if (name.isEmpty()) {
            superPower.setName("none");
        } else {
            superPower.setName(name);
        }

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

        return "redirect:superPowers";
    }

    @GetMapping("editSuperPower")
    public String editSuperPower(Integer superPowerId, Model model) {

        SuperPower superPower = service.getSuperPowerById(superPowerId);
        model.addAttribute("superPower", superPower);

        return "/superPower/editSuperPower";
    }

    @PostMapping("editSuperPower")
    public String performEditSuperPower(@Valid SuperPower superPower, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "/superPower/editSuperPower";
        }

        service.updateSuperPower(superPower);
        redirectAttributes.addAttribute("superPowerId", superPower.getId());

        return "redirect:superPowerDetails";
    }

    @GetMapping("deleteSuperPowerConfirm")
    public String deleteSuperPowerConfirm(Integer superPowerId, Integer heroId, Model model) {

        model.addAttribute("superPowerId", superPowerId);
        model.addAttribute("heroId", heroId);

        return "/superPower/deleteSuperPowerConfirm";
    }

    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(Integer superPowerId, Integer heroId) {

        service.deleteSuperPowerById(superPowerId);

        return "redirect:superPowers";
    }

    @GetMapping("superPowerDetails")
    public String superPowerDetails(Integer superPowerId, Model model) {

        SuperPower superPower = service.getSuperPowerById(superPowerId);

        Hero hero = service.getSuperPowerDetails(superPowerId);

        model.addAttribute("hero", hero);
        model.addAttribute("superPower", superPower);

        return "superPower/superPowerDetails";
    }

    @GetMapping("editSuperPowerHero")
    public String editSuperPowerHero(Integer superPowerId, Integer heroId, Model model) {

        List<Hero> heros = service.getAllHeros();
        model.addAttribute("heros", heros);
        model.addAttribute("heroId", heroId);
        model.addAttribute("superPowerId", superPowerId);

        return "superPower/editSuperPowerHero";
    }

    @PostMapping("editSuperPowerHero")
    public String performSuperPowerHero(Model model, Integer newHeroId, Integer heroId, Integer superPowerId,
            RedirectAttributes redirectAttributes) {

        Hero hero = service.getHeroById(newHeroId);

        SuperPower superPower = service.getSuperPowerById(superPowerId);

        hero.setSuperPower(superPower.getName());

        service.updateSuperPowerHero(hero, heroId);

        redirectAttributes.addAttribute("superPowerId", superPower.getId());
        return "redirect:superPowerDetails";
    }
}
