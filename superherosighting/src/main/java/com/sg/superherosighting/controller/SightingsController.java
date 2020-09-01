/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sighting;
import com.sg.superherosighting.service.ServiceLayer;
import java.time.LocalDateTime;
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
public class SightingsController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("sightings")
    public String getAllSightings(Model model) {

        List<Sighting> sightings = service.getAllSightings();
        List<Hero> heros = service.getAllHeros();
        List<Location> locations = service.getAllLocations();

        System.out.println("service.getSightingViolations() " + service.getSightingViolations().size());
        model.addAttribute("sightings", sightings);

        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", service.getSightingViolations());

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {

        //get values
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        //parse date
        if (!(date == null)) {
            LocalDateTime sightingDate = LocalDateTime.parse(date);
            sighting.setLocalDate(sightingDate);
        }

        if (date == null || date.isEmpty()) {

            service.validateSighting(sighting);

            return "redirect:/sightings";
        }
        //set them
        sighting.setHero(service.getHeroById((Integer.parseInt(heroId))));
        sighting.setLocation(service.getLocationById((Integer.parseInt(locationId))));

        //check if empty then add
        if (service.validateSighting(sighting).isEmpty()) {
            //add to db
            service.addSighting(sighting);
        }

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer locationId, Integer heroId, Model model) {

        Sighting sighting = service.getSightingById(heroId, locationId);

        List<Hero> heros = service.getAllHeros();
        List<Location> locations = service.getAllLocations();

        model.addAttribute("sighting", sighting);
        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);

        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request) {

        System.out.println("Top sighting performEditSighting " + sighting.toString());

        String date = request.getParameter("date");
        String existingHeroId = request.getParameter("existingHeroId");
        String existingLocationId = request.getParameter("existingLocationId");

        //parse date
        LocalDateTime sightingDate = LocalDateTime.parse(date);

        //set date to object
        sighting.setLocalDate(sightingDate);

        if (result.hasErrors()) {
            return "editSighting";
        }
        service.updateSighting(sighting, Integer.parseInt(existingHeroId), Integer.parseInt(existingLocationId));

        return "redirect:/sightings";
    }

    @GetMapping("deleteSightingConfirm")
    public String deleteSightingConfirm(Integer id, Model model) {

//        model.addAttribute("heroId", id);
        return "deleteSightingConfirm";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer id) {

//        service.deleteSightingById(id);
        return "redirect:/sightings";
    }

}
