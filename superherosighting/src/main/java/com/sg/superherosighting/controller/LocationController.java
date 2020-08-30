/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.service.ServiceLayer;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Valid;
import javax.validation.Validation;
import javax.validation.Validator;
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
    ServiceLayer service;

    Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();

    @GetMapping("locations")
    public String getAllLocations(Model model) {

        List<Location> locations = service.getAllLocations();

        System.out.println("locationViolations " + locationViolations.size());

        model.addAttribute("locations", locations);
        model.addAttribute("errors", locationViolations);

        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(HttpServletRequest request) {

        //location parms from client
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");
        String latitude = request.getParameter("latitude");
        String longitude = request.getParameter("longitude");

        Location location = new Location();

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        location.setName(name);
        location.setDescription(description);
        location.setAddress(address);
        location.setLatitude(latitude);
        location.setLongitude(longitude);

        //check if valid
        locationViolations = validate.validate(location);

        //check if empty then add
        if (locationViolations.isEmpty()) {
            //add location
            service.addLocation(location);
        }

        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer id, Model model) {
        Location location = service.getLocationById(id);
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {

        if (result.hasErrors()) {
            return "editLocation";
        }

        service.updateLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("deleteLocationConfirm")
    public String deleteLocationConfirm(Integer id, Model model) {

        model.addAttribute("locationId", id);

        return "deleteLocationConfirm";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer id) {

        service.deleteLocationById(id);

        return "redirect:/locations";
    }
}
