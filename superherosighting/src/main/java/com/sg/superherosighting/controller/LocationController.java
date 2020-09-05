/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.service.ServiceLayer;
import java.time.LocalDateTime;
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
public class LocationController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("location")
    public String getLocationPage(Model model) {

        List<Hero> heros = service.getAllHeros();

        System.out.println("service.getLocationViolations() " + service.getLocationViolations());

        model.addAttribute("heros", heros);
        model.addAttribute("errors", service.getLocationViolations());

        return "/location/location";
    }

    @GetMapping("allLocations")
    public String getAllLocations(Model model) {

        List<Location> locations = service.getAllLocations();

        model.addAttribute("locations", locations);

        return "/location/listLocations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request, @Valid Location location, BindingResult valResult) {

        System.out.println("location location top " + location.toString());

        String date = request.getParameter("date");

        //heros ids
        String[] herosIds = request.getParameterValues("herosId");

        System.out.println("herosIds " + Arrays.toString(herosIds));

        List<Hero> heros = new ArrayList<>();

        if (herosIds != null) {
            for (String heroId : herosIds) {
                heros.add(service.getHeroById(Integer.parseInt(heroId)));
            }
        }

        System.out.println("location object " + location.toString());

        System.out.println("date  value : " + date);

        //parse date
        if (!(date.isEmpty())) {
            LocalDateTime locationDate = LocalDateTime.parse(date);

            System.out.println("sightingDate " + locationDate);

            location.setLocalDate(locationDate);

            System.out.println("sighting --" + locationDate.toString());
        }

        //check if empty then add
        if (service.validateLocation(location).isEmpty()) {

            if (herosIds != null) {
                //set orgs
                location.setHeros(heros);

            }
            //add location
            service.addLocation(location);

            if (herosIds != null) {
                service.insertLocationHero(location);
            }
        }

        return "redirect:allLocations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = service.getLocationById(id);
        model.addAttribute("location", location);
        return "/location/editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {

        if (result.hasErrors()) {
            return "/location/editLocation";
        }

        service.updateLocation(location);
        return "redirect:/location/location";
    }

    @GetMapping("deleteLocationConfirm")
    public String deleteLocationConfirm(Integer id, Model model) {

        model.addAttribute("locationId", id);

        return "/location/deleteLocationConfirm";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {

        service.deleteLocationById(id);

        return "redirect:/location/location";
    }

    @GetMapping("locationDetails")
    public String locationDetails(Integer locationId, Model model) {

        Location location = service.getLocationDetails(locationId);

        System.out.println("location at end point " + location.toString());

        model.addAttribute("locationDetails", location);

        return "/location/locationDetails";
    }
}
