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

        model.addAttribute("sightings", sightings);

        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(Sighting sighting, HttpServletRequest request) {

        //get values
        String heroId = request.getParameter("heroId");
        String locationId = request.getParameter("locationId");
        String date = request.getParameter("date");

        System.out.println("heroId " + heroId);
        System.out.println("locationId " + locationId);
        System.out.println("date " + date);

        //parse date
        LocalDateTime sightingDate = LocalDateTime.parse(date);

        //get hero and location
        Hero hero = service.getHeroById((Integer.parseInt(heroId)));
        Location location = service.getLocationById((Integer.parseInt(locationId)));

        System.out.println("hero " + hero.toString());
        System.out.println("date " + date);

        //set them
        sighting.setHero(hero);
        sighting.setLocation(location);
        sighting.setLocalDate(sightingDate);

        //add to db
        service.addSighting(sighting);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(Integer id, Model model) {
//        Sighting hero = service.getSightingById(id);
//        model.addAttribute("hero", hero);
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting hero, BindingResult result) {

//        if (result.hasErrors()) {
//            return "editSighting";
//        }
//
//        service.updateSighting(hero);
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
