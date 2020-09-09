/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Sighting;
import com.sg.superherosighting.service.SightingSeviceLayer;
import com.sg.superherosighting.service.util.TimeAgo;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 *
 * @author louie
 */
@Controller
public class IndexController {

    @Autowired
    private SightingSeviceLayer sightingService;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("/")
    public String index(Model model) {

        List<Sighting> sightings = sightingService.getAllSightings();

        TimeAgo tago = new TimeAgo();

        sightings.forEach(sighting -> {
            sighting.setTimeAgo(tago.format(sighting.getLocalDate()));
        });

        model.addAttribute("sightings", sightings);
        return "index";
    }

}
