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
import com.sg.superherosighting.service.HeroSeviceLayer;
import com.sg.superherosighting.service.LocationSeviceLayer;
import com.sg.superherosighting.service.OrganizationSeviceLayer;
import com.sg.superherosighting.service.SuperPowerSeviceLayer;
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
    private HeroSeviceLayer heroService;

    @Autowired
    private OrganizationSeviceLayer organizationService;

    @Autowired
    private LocationSeviceLayer locationService;

    @Autowired
    private SuperPowerSeviceLayer superPowerService;

    /**
     *
     * @param model
     * @return hero page
     */
    @GetMapping("hero")
    public String getHeroPage(Model model) {

        //return errors
        model.addAttribute("errors", heroService.getHeroViolations());

        return "/hero/hero";
    }

    /**
     *
     * @param model
     * @return listheroPage
     */
    @GetMapping("allHeros")
    public String getAllHeros(Model model) {
        //get all heros
        List<Hero> heros = heroService.getAllHeros();

        model.addAttribute("heros", heros);

        return "/hero/listHeros";
    }

    /**
     *
     * @param hero - hero object from client
     * @param result
     * @param request
     * @return allHeros page
     */
    @PostMapping("addHero")
    public String addHero(@Valid Hero hero, BindingResult result, HttpServletRequest request) {

        //check for errors
        if (result.hasErrors()) {
            heroService.validateHero(hero);
            return "redirect:hero";
        }

        //add hero
        heroService.addHero(hero);

        return "redirect:allHeros";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("heroDetails")
    public String heroDetails(Integer id, Model model) {

        //get hero details
        Hero hero = heroService.getHeroDetails(id);

        model.addAttribute("heroDetails", hero);

        return "/hero/heroDetails";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("editHero")
    public String editHero(Integer id, Model model) {

        //get hero and all orgs and locations
        Hero hero = heroService.getHeroById(id);
        List<Organization> organizations = organizationService.getAllOrganizations();
        List<Location> locations = locationService.getAllLocations();
        List<SuperPower> superPowers = superPowerService.getAllSuperPowers();

        model.addAttribute("superPowers", superPowers);
        model.addAttribute("locations", locations);
        model.addAttribute("organizations", organizations);
        model.addAttribute("hero", hero);

        model.addAttribute("errors", heroService.getHeroViolations());

        return "/hero/editHero";
    }

    /**
     *
     * @param hero
     * @param result
     * @param redirectAttributes
     * @param request
     * @param model
     * @return
     */
    @PostMapping("editHero")
    public String performEditHero(@Valid Hero hero, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) {

        //check for errors
        if (result.hasErrors()) {
            //validate
            heroService.validateHero(hero);
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

        heroService.updateHero(hero);

        redirectAttributes.addAttribute("id", hero.getId());

        return "redirect:heroDetails";
    }

    /**
     *
     * @param id
     * @param model
     * @return
     */
    @GetMapping("deleteHeroConfirm")
    public String deleteHeroConfirm(Integer id, Model model) {

        model.addAttribute("heroId", id);

        return "/hero/deleteHeroConfirm";
    }

    /**
     *
     * @param id
     * @return
     */
    @GetMapping("deleteHero")
    public String deleteHero(Integer id) {

        heroService.deleteHeroById(id);

        return "redirect:allHeros";
    }

    private void getAndSetOrganizations(List<Organization> organizations, String[] organizationIds, Hero hero) {

        if (organizationIds != null) {
            //get orgs
            for (String organizationId : organizationIds) {

                organizations.add(organizationService.getOrganizationById(Integer.parseInt(organizationId)));
            }
            //set orgs
            hero.setOrganizations(organizations);
        }

    }

    private void getAndSetLocationAndDate(String locationId, String date, Hero hero) {

        //list of locations
        List<Location> locations = new ArrayList<>();

        //check if date and location id is not null
        if (locationId != null && !locationId.equals("Select a location") && !date.isBlank()) {

            //parse date
            LocalDateTime sightingDate = LocalDateTime.parse(date);

            //get location by id
            Location location = locationService.getLocationById(Integer.parseInt(locationId));

            //set date to location
            location.setLocalDate(sightingDate);
            //add location to location array
            locations.add(location);
            //set location to hero
            hero.setLocations(locations);
        }

    }

}
