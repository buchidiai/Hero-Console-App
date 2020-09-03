/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Sighting;
import com.sg.superherosighting.service.ServiceLayer;
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
    private ServiceLayer service;

    @GetMapping("/")
    public String index(Model model) {

        System.out.println("here");

        List<Sighting> sightings = service.getAllSightings();

        TimeAgo tago = new TimeAgo();

        sightings.forEach(sighting -> {
            sighting.setTimeAgo(tago.format(sighting.getLocalDate()));
        });

        model.addAttribute("sightings", sightings);

        System.out.println("here2");

        return "index";
    }

}
