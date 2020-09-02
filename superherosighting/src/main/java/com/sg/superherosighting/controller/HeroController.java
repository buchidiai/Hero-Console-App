/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.SuperPower;
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

/**
 *
 * @author louie
 */
@Controller
public class HeroController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("heros")
    public String getAllHeros(Model model) {

        List<Hero> heros = service.getAllHeros();
        List<Organization> organizations = service.getAllOrganizations();

        model.addAttribute("heros", heros);
        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", service.getHeroViolations());

        return "heros";
    }

    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {

        //hero
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        //organization ids
        String[] organizationIds = request.getParameterValues("organizationIds");

        System.out.println("organizationIds " + Arrays.toString(organizationIds));

        //super power
        String heroSuperPower = request.getParameter("superPower");
        SuperPower superPower = new SuperPower();

        if (heroSuperPower.isEmpty()) {

            superPower.setName("none");
        } else {
            superPower.setName(heroSuperPower);
        }

        superPower = service.addSuperPower(superPower);

        Hero hero = new Hero();
        hero.setName(name);
        hero.setDescription(description);
        hero.setSuperPower(String.valueOf(superPower.getId()));

        List<Organization> organizations = new ArrayList<>();

        if (organizationIds != null) {
            for (String organizationId : organizationIds) {

                organizations.add(service.getOrganizationById(Integer.parseInt(organizationId)));
            }

        }

        //check if empty then add
        if (service.validateHero(hero).isEmpty()) {

            if (organizationIds != null) {
                //set orgs
                hero.setOrganization(organizations);

            }

            //add hero
            service.addHero(hero);
            //add bridge table relationship

            if (organizationIds != null) {
                service.insertHero_Organization(hero);
            }

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
