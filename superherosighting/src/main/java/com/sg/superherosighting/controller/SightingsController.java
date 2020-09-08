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
public class SightingsController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("sighting")
    public String getSightingPage(Model model) {

        List<Sighting> sightings = service.getAllSightings();
        List<Hero> heros = service.getAllHeros();
        List<Location> locations = service.getAllLocations();

        model.addAttribute("sightings", sightings);
        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);
        model.addAttribute("errors", service.getSightingViolations());

        return "/sighting/sighting";
    }

    @GetMapping("allSightings")
    public String getAllSightings(Model model) {

        List<Sighting> sightings = service.getAllSightings();

        model.addAttribute("sightings", sightings);
        return "sighting/listSightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            service.validateSighting(sighting);
            return "redirect:sighting";
        }

        //get hero values
        String[] heroIds = request.getParameterValues("heroId");

        //set and set heros to sighting object
        getAndSetHeros(heroIds, sighting);

        getandSetDate(sighting);

        service.addSighting(sighting);

        return "redirect:allSightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer locationId, Integer heroId, Integer sightingId, Model model) {

        Sighting sighting = service.getSightingById(sightingId);

        List<Hero> heros = service.getAllHeros();
        List<Location> locations = service.getAllLocations();

        model.addAttribute("sighting", sighting);
        model.addAttribute("sightingId", sightingId);
        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);

        return "/sighting/editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(Sighting sighting, BindingResult result, Integer sightingId, HttpServletRequest request,
            RedirectAttributes redirectAttributes) {

        String date = request.getParameter("date");
        String existingHeroId = request.getParameter("existingHeroId");
        String existingLocationId = request.getParameter("existingLocationId");

        //parse date
        LocalDateTime sightingDate = LocalDateTime.parse(date);

        //set date to object
        sighting.setLocalDate(sightingDate);
        sighting.setId(sightingId);

        service.updateSighting(sighting, Integer.parseInt(existingHeroId), Integer.parseInt(existingLocationId));

        redirectAttributes.addAttribute("sightingId", sighting.getId());

        return "redirect:sightingDetails";
    }

    @GetMapping("deleteSightingConfirm")
    public String deleteSightingConfirm(Integer heroId, Integer locationId, Integer sightingId, Model model) {

        model.addAttribute("heroId", heroId);
        model.addAttribute("locationId", locationId);
        model.addAttribute("sightingId", sightingId);

        return "/sighting/deleteSightingConfirm";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(Integer heroId, Integer locationId, Integer sightingId) {

        service.deleteSightingById(heroId, locationId, sightingId);
        return "redirect:allSightings";
    }

    @GetMapping("sightingDetails")
    public String sightingDetails(Integer sightingId, Model model) {

        List<Sighting> sightings = service.getAllSightings();

        Sighting sightingDetails = null;

        //get specific sighting details from list of all details
        for (Sighting sighting : sightings) {

            if (sighting.getId() == sightingId) {
                sightingDetails = sighting;
                break;
            }
        }

        model.addAttribute("sightingDetails", sightingDetails);
        return "/sighting/sightingDetails";
    }

    private void getAndSetHeros(String[] heroIds, Sighting sighting) {

        List<Hero> heros = new ArrayList<>();

        if (heroIds != null) {

            //get heros
            for (String heroId : heroIds) {

                heros.add(service.getHeroById(Integer.parseInt(heroId)));
            }
            //set orgs
            sighting.setHeros(heros);
        }

    }

    private void getandSetDate(Sighting sighting) {

        if (!sighting.getDate().isBlank()) {

            //parse date
            LocalDateTime sightingDate = LocalDateTime.parse(sighting.getDate());

            //set date to location
            sighting.setLocalDate(sightingDate);
            //add location to location array
        }

    }

}
