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
public class HeroController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("hero")
    public String getHeroPage(Model model) {

        List<Organization> organizations = service.getAllOrganizations();

        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", service.getHeroViolations());

        return "/hero/hero";
    }

    @GetMapping("heros")
    public String getAllHeros(Model model) {

        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);

        return "/hero/heros";
    }

    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {

        //hero
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        //organization ids
        String[] organizationIds = request.getParameterValues("organizationIds");

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
                hero.setOrganizations(organizations);
            }
            //add hero
            service.addHero(hero);
            //add bridge table relationship

            if (organizationIds != null) {
                service.insertHeroOrganization(hero);
            }
        }

        return "redirect:hero";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {

        Hero hero = service.getHeroById(id);

        model.addAttribute("hero", hero);
        return "/hero/editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "/hero/editHero";
        }

        service.updateHero(hero);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }

    @GetMapping("deleteHeroConfirm")
    public String deleteHeroConfirm(Integer id, Model model) {

        model.addAttribute("heroId", id);

        return "/hero/deleteHeroConfirm";
    }

    @GetMapping("deleteHero")
    public String deleteHero(Integer id) {

        service.deleteHeroById(id);

        return "redirect:hero";
    }

    @GetMapping("heroDetails")
    public String heroDetails(Integer id, Model model) {

        Hero hero = service.getHeroDetails(id);

        model.addAttribute("heroDetails", hero);
        return "/hero/heroDetails";
    }

    @GetMapping("editHeroOrganization")
    public String editHeroOrganization(int organizationId, int heroId, Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        model.addAttribute("organizationId", organizationId);
        model.addAttribute("heroId", heroId);

        return "hero/editHeroOrganization";
    }

    @PostMapping("editHeroOrganization")
    public String performEditHeroOrganization(Model model, HttpServletRequest request, Integer newOrganizationId, int heroId, int originalId,
            RedirectAttributes redirectAttributes) {

        System.out.println("originalId " + originalId);

        Hero hero = service.getHeroById(heroId);

        Organization organization = service.getOrganizationById(newOrganizationId);

        service.updateHeroOrganization(hero, organization, originalId);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }
}
