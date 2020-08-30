/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import com.sg.superherosighting.dao.HeroDao;

/**
 *
 * @author louie
 */
@Controller
public class SightingsController {

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao characterDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    @GetMapping("sightings")
    public String getAllCharacters() {

        return "sightings";
    }

}