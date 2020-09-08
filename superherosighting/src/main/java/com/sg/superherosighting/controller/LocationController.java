/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.service.HeroSeviceLayer;
import com.sg.superherosighting.service.LocationSeviceLayer;
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
public class LocationController {

    @Autowired
    private LocationSeviceLayer locationService;

    @Autowired
    private HeroSeviceLayer heroService;

    @GetMapping("location")
    public String getLocationPage(Model model) {

        List<Hero> heros = heroService.getAllHeros();

        model.addAttribute("heros", heros);
        model.addAttribute("errors", locationService.getLocationViolations());

        return "/location/location";
    }

    @GetMapping("allLocations")
    public String getAllLocations(Model model) {

        List<Location> locations = locationService.getAllLocations();

        model.addAttribute("locations", locations);

        return "/location/listLocations";
    }

    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            locationService.validateLocation(location);
            return "redirect:location";
        }

        locationService.addLocation(location);

        return "redirect:allLocations";
    }

    @GetMapping("editLocation")
    public String editLocation(Integer locationId, Model model) {

        Location location = locationService.getLocationById(locationId);

        model.addAttribute("location", location);
        return "/location/editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        if (result.hasErrors()) {
            return "/location/editLocation";
        }

        locationService.updateLocation(location);

        redirectAttributes.addAttribute("locationId", location.getId());
        return "redirect:locationDetails";
    }

    @GetMapping("editLocationHero")
    public String editLocationHero(Integer locationId, Integer heroId, Model model) {

        List<Hero> locationheros = locationService.getLocationDetails(locationId).getHeros();

        List<Hero> allheros = heroService.getAllHeros();

        locationheros.stream().filter(l -> (allheros.contains(l))).filter(l -> (l.getId() != locationId)).forEachOrdered(l -> {
            allheros.remove(l);
        });

        Location location = locationService.getLocationById(locationId);

        model.addAttribute("heros", allheros);
        model.addAttribute("heroId", heroId);
        model.addAttribute("locationId", location.getId());

        return "/location/editLocationHero";
    }

    @PostMapping("editLocationHero")
    public String performEditLocationHero(Integer newHeroId, Integer locationId, Integer heroId, RedirectAttributes redirectAttributes) {

        Location location = locationService.getLocationById(locationId);

        Hero hero = heroService.getHeroById(newHeroId);

        locationService.updateLocationHero(hero, location, heroId);

        redirectAttributes.addAttribute("locationId", location.getId());

        return "redirect:locationDetails";
    }

    @GetMapping("deleteLocationConfirm")
    public String deleteLocationConfirm(Integer locationId, Model model) {

        model.addAttribute("locationId", locationId);

        return "/location/deleteLocationConfirm";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(Integer locationId) {

        locationService.deleteLocationById(locationId);

        return "redirect:allLocations";
    }

    @GetMapping("deleteLocationHeroConfirm")
    public String deleteLocationHeroConfirm(Integer heroId, Integer locationId, Model model) {

        model.addAttribute("heroId", heroId);
        model.addAttribute("locationId", locationId);

        return "/location/deleteLocationHeroConfirm";
    }

    @GetMapping("deleteLocationHero")
    public String deleteLocationHero(Integer heroId, Integer locationId, Model model, RedirectAttributes redirectAttributes) {

        Hero hero = heroService.getHeroById(heroId);

        Location location = locationService.getLocationById(locationId);

        heroService.deleteHeroLocation(hero, location);

        redirectAttributes.addAttribute("locationId", location.getId());

        return "redirect:locationDetails";
    }

    @GetMapping("locationDetails")
    public String locationDetails(Integer locationId, Model model) {

        Location location = locationService.getLocationDetails(locationId);

        model.addAttribute("locationDetails", location);

        return "/location/locationDetails";
    }
}
