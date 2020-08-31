/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sighting;
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

        List<Hero> heros = service.getAllHeros();
        List<Location> locations = service.getAllLocations();

        model.addAttribute("heros", heros);
        model.addAttribute("locations", locations);

        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(HttpServletRequest request) {

        //super pwer
        String heroSuperPower = request.getParameter("heroSuperPower");

        SuperPower superPower = new SuperPower();
        superPower.setName(heroSuperPower);

        superPower = service.addSuperPower(superPower);

        //hero
        String heroName = request.getParameter("heroName");
        String heroDescription = request.getParameter("heroDescription");

        //photo
        String heroPhoto = request.getParameter("heroPhoto");

        Hero hero = new Hero();
        hero.setName(heroName);
        hero.setDescription(heroDescription);
        hero.setSuperPower(String.valueOf(superPower.getId()));

        //check if empty then add
        if (service.validateHero(hero).isEmpty()) {
            //add hero
            service.addHero(hero);
        }

        //location
        String locationName = request.getParameter("locationName");
        String locationDescription = request.getParameter("locationDescription");
        String locationAddress = request.getParameter("locationAddress");
        String locationLatitude = request.getParameter("locationLatitude");
        String locationLongitude = request.getParameter("locationLongitude");

        Location location = new Location();

        location.setName(locationName);
        location.setDescription(locationDescription);
        location.setAddress(locationAddress);
        location.setLatitude(locationLatitude);
        location.setLongitude(locationLongitude);

        //check if empty then add
        if (service.validateLocation(location).isEmpty()) {
            //add location
            service.addLocation(location);
        }

        //date
        String date = request.getParameter("date");

        System.out.println("date " + date);

        Sighting sighting = new Sighting();

        sighting.setHero(hero);

        sighting.setLocation(location);

//        sighting.setLocalDate("sds");
        System.out.println("sighting " + sighting.toString());

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
