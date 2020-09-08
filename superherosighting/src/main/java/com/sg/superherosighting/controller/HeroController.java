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
import java.time.LocalDateTime;
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

        //return errors
        model.addAttribute("errors", service.getHeroViolations());

        return "/hero/hero";
    }

    @GetMapping("allHeros")
    public String getAllHeros(Model model) {
        //get all heros
        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);

        return "/hero/listHeros";
    }

    @PostMapping("addHero")
    public String addHero(@Valid Hero hero, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            service.validateHero(hero);
            return "redirect:hero";
        }

        //check if hero is empty then add
        if (service.validateHero(hero).isEmpty()) {
            //add hero
            service.addHero(hero);
        }

        return "redirect:allHeros";
    }

    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {

        //get hero and all orgs and locations
        Hero hero = service.getHeroById(id);
        List<Organization> organizations = service.getAllOrganizations();
        List<Location> locations = service.getAllLocations();
        List<SuperPower> superPowers = service.getAllSuperPowers();

        model.addAttribute("superPowers", superPowers);
        model.addAttribute("locations", locations);
        model.addAttribute("organizations", organizations);
        model.addAttribute("locations", locations);
        model.addAttribute("hero", hero);

        //check if super power is not null in case it was deleted
//        if (hero.getSuperPower() != null) {
//            model.addAttribute("superPower", hero.getSuperPower());
//        }
        model.addAttribute("errors", service.getHeroViolations());

        return "/hero/editHero";
    }

    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) {

        //check for errors
        if (result.hasErrors()) {
            //validate
            service.validateHero(hero);
            //redirect with id
            redirectAttributes.addAttribute("id", hero.getId());
            return "redirect:editHero";
        }

        //organization ids
        String[] organizationIds = request.getParameterValues("organizationIds");

        //location id
        String locationId = request.getParameter("locationId");

        //date
        String date = request.getParameter("date");

        //list of orgs
        List<Organization> organizations = new ArrayList<>();

        //get and set Organizations
        getAndSetOrganizations(organizations, organizationIds, hero);

        //get and set locations
        getAndSetLocationAndDate(locationId, date, hero);

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

        System.out.println("hero getLocations " + hero.getLocations());
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
    public String deleteHeroLocationConfirm(Integer heroId, Integer locationId, Integer sightingId, Model model) {

        model.addAttribute("heroId", heroId);
        model.addAttribute("locationId", locationId);
        model.addAttribute("sightingId", sightingId);

        return "/hero/deleteHeroLocationConfirm";
    }

    @GetMapping("deleteHeroLocation")
    public String deleteHeroLocation(Integer heroId, Integer locationId, Integer sightingId, Model model,
            RedirectAttributes redirectAttributes) {

        Hero hero = service.getHeroById(heroId);

        Location location = service.getLocationById(locationId);
        location.setSightingId(sightingId);

        service.deleteHeroLocation(hero, location);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }

    private void getAndSetOrganizations(List<Organization> organizations, String[] organizationIds, Hero hero) {

        if (organizationIds != null) {
            //get orgs
            for (String organizationId : organizationIds) {

                organizations.add(service.getOrganizationById(Integer.parseInt(organizationId)));
            }
            //set orgs
            hero.setOrganizations(organizations);
        }

    }

    private void getAndSetLocationAndDate(String locationId, String date, Hero hero) {

        //list of locations
        List<Location> locations = new ArrayList<>();

        //check if date and location id is not null
        if (locationId != null && !date.isBlank()) {

            //parse date
            LocalDateTime sightingDate = LocalDateTime.parse(date);

            //get location by id
            Location location = service.getLocationById(Integer.parseInt(locationId));

            //set date to location
            location.setLocalDate(sightingDate);
            //add location to location array
            locations.add(location);
            //set location to hero
            hero.setLocations(locations);
        }

    }

}
