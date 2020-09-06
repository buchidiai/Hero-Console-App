/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
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

    @GetMapping("allHeros")
    public String getAllHeros(Model model) {

        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);

        return "/hero/listHeros";
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
        hero.setSuperPower(superPower.getName());
        hero.setSuperPower_id(superPower.getId());

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

        return "redirect:allHeros";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {

        Hero hero = service.getHeroById(id);

        model.addAttribute("hero", hero);
        model.addAttribute("superPower", hero.getSuperPower());

        return "/hero/editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) {

        //super power
        String super_Power = request.getParameter("superPower");

        if (super_Power.isEmpty()) {
            super_Power = "none";
        }

        hero.setSuperPower(super_Power);

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

        return "redirect:allHeros";
    }

    @GetMapping("heroDetails")
    public String heroDetails(Integer id, Model model) {

        Hero hero = service.getHeroDetails(id);

        model.addAttribute("heroDetails", hero);

        return "/hero/heroDetails";
    }

    @GetMapping("editHeroOrganization")
    public String editHeroOrganization(Integer organizationId, Integer heroId, Model model) {
        List<Organization> organizations = service.getAllOrganizations();
        model.addAttribute("organizations", organizations);
        model.addAttribute("organizationId", organizationId);
        model.addAttribute("heroId", heroId);

        return "hero/editHeroOrganization";
    }

    @PostMapping("editHeroOrganization")
    public String performEditHeroOrganization(Model model, Integer newOrganizationId, Integer heroId, Integer organizationId,
            RedirectAttributes redirectAttributes) {

        Hero hero = service.getHeroById(heroId);

        Organization organization = service.getOrganizationById(newOrganizationId);

        service.updateHeroOrganization(hero, organization, organizationId);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }

    @GetMapping("deleteHeroOrganizationConfirm")
    public String deleteHeroOrganizationConfirm(Integer heroId, Integer organizationId, Model model) {

        model.addAttribute("heroId", heroId);
        model.addAttribute("organizationId", organizationId);

        return "/hero/deleteHeroOrganizationConfirm";
    }

    @GetMapping("deleteHeroOrganization")
    public String deleteHeroOrganization(Integer heroId, Integer organizationId, Model model, RedirectAttributes redirectAttributes) {

        Hero hero = service.getHeroById(heroId);

        Organization organization = service.getOrganizationById(organizationId);

        service.deleteHeroOrganization(hero, organization);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }

    @GetMapping("editHeroLocation")
    public String editHeroLocation(Integer locationId, Integer heroId, Model model) {

        List<Location> heroLocations = service.getHeroDetails(heroId).getLocations();

        List<Location> allLocations = service.getAllLocations();

        heroLocations.stream().filter(l -> (allLocations.contains(l))).filter(l -> (l.getId() != locationId)).forEachOrdered(l -> {
            allLocations.remove(l);
        });

        Hero hero = service.getHeroById(heroId);

        model.addAttribute("locations", allLocations);
        model.addAttribute("locationId", locationId);
        model.addAttribute("heroId", hero.getId());

        return "/hero/editHeroLocation";
    }

    @PostMapping("editHeroLocation")
    public String performEditHeroLocation(Integer newLocationId, Integer locationId, Integer heroId, RedirectAttributes redirectAttributes) {

        Hero hero = service.getHeroById(heroId);

        Location location = service.getLocationById(newLocationId);

        service.updateHeroLocation(hero, location, locationId);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }

    @GetMapping("deleteHeroLocationConfirm")
    public String deleteHeroLocationConfirm(Integer heroId, Integer locationId, Model model) {

        model.addAttribute("heroId", heroId);
        model.addAttribute("locationId", locationId);

        return "/hero/deleteHeroLocationConfirm";
    }

    @GetMapping("deleteHeroLocation")
    public String deleteHeroLocation(Integer heroId, Integer locationId, Model model, RedirectAttributes redirectAttributes) {

        Hero hero = service.getHeroById(heroId);

        Location location = service.getLocationById(locationId);

        service.deleteHeroLocation(hero, location);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }
}
