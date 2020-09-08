/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
import com.sg.superherosighting.exceptions.SuperHeroDuplicateKeyException;
import com.sg.superherosighting.service.ServiceLayer;
import java.util.ArrayList;
import java.util.Arrays;
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

        model.addAttribute("errors", service.getSuperPowerViolations());

        return "/superPower/superPower";
    }

    @GetMapping("allSuperPowers")
    public String getAllSuperPowers(Model model) {

        List<SuperPower> superPowers = service.getAllSuperPowers();

        model.addAttribute("superPowers", superPowers);

        return "/superPower/listSuperPowers";
    }

    @PostMapping("addSuperPower")
    public String addSuperPower(@Valid SuperPower superPower, BindingResult result) {

        if (result.hasErrors()) {
            service.validateSuperPower(superPower);
            return "redirect:superPower";
        }

        superPower = service.addSuperPower(superPower);

        return "redirect:allSuperPowers";
    }

    @GetMapping("editSuperPower")
    public String editSuperPower(Integer superPowerId, Model model) {

        SuperPower superPower = service.getSuperPowerById(superPowerId);

        List<Hero> heros = service.getAllHeros();
        model.addAttribute("heros", heros);
        model.addAttribute("superPower", superPower);
        model.addAttribute("errors", service.getSuperPowerViolations());

        return "/superPower/editSuperPower";
    }

    @PostMapping("editSuperPower")
    public String performEditSuperPower(@Valid SuperPower superPower, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) throws SuperHeroDuplicateKeyException {

        if (result.hasErrors()) {

            service.validateSuperPower(superPower);
            redirectAttributes.addAttribute("superPowerId", superPower.getId());

            return "redirect:editSuperPower";
        }

        //heros ids
        String[] heroIds = request.getParameterValues("heroId");

        System.out.println("heroIds " + Arrays.toString(heroIds));

        getAndSetHeros(heroIds, superPower);

        service.updateSuperPower(superPower, result);

//        if (result.hasErrors()) {
//
//            service.validateSuperPower(superPower);
//
//            model.addAttribute("errors", service.getSuperPowerViolations());
//
//            redirectAttributes.addAttribute("superPowerId", superPower.getId());
//
//            return "redirect:editSuperPower";
//        }
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

        return "redirect:allSuperPowers";
    }

    @GetMapping("superPowerDetails")
    public String superPowerDetails(Integer superPowerId, Model model) {

        SuperPower superPower = service.getSuperPowerById(superPowerId);

        List<Hero> heros = service.getSuperPowerDetails(superPowerId);

        model.addAttribute("heros", heros);
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
        hero.setSuperPower_id(superPower.getId());

        service.updateSuperPowerHero(hero, heroId);

        redirectAttributes.addAttribute("superPowerId", superPower.getId());
        return "redirect:superPowerDetails";
    }

    private void getAndSetHeros(String[] heroIds, SuperPower superPower) {

        List<Hero> heros = new ArrayList<>();

        if (heroIds != null) {
            System.out.println("no null");
            //get orgs
            for (String heroId : heroIds) {

                heros.add(service.getHeroById(Integer.parseInt(heroId)));
            }
            //set orgs
            superPower.setHeros(heros);
        }

        System.out.println("superPower -- " + superPower.toString());

    }
}
