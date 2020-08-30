/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
import com.sg.superherosighting.service.ServiceLayer;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

/**
 *
 * @author louie
 */
@Controller
public class HeroController {

    @Autowired
    ServiceLayer service;

    @GetMapping("heros")
    public String getAllHeros(Model model) {

        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);

        return "heros";
    }

    @PostMapping("addHero")
    public String addHero(HttpServletRequest request) {

        //super power params
        String super_Power = request.getParameter("superPower");

        //hero parms
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower(super_Power);

        System.out.println("superPower " + superPower.toString());

        //add super power
        superPower = service.addSuperPower(superPower);
        ;
        Hero hero = new Hero();

        hero.setName(name);
        hero.setDescription(description);
        hero.setSuperPower(String.valueOf(superPower.getId()));

        System.out.println("hero " + hero.toString());

//        System.out.println("hero before add  " + hero.toString());
        //add charater
        service.addHero(hero);

        return "redirect:/heros";
    }

//    @GetMapping("editHero")
//    public String editHero(HttpServletRequest request, Model model) {
//        int id = Integer.parseInt(request.getParameter("id"));
//        Hero hero = service.getHeroById(id);
//
//        model.addAttribute("hero", hero);
//        return "editHero";
//    }
    @PostMapping("editHero")
    public String performEditHero(HttpServletRequest request) {

        int id = Integer.parseInt(request.getParameter("id"));
        Hero hero = service.getHeroById(id);

        hero.setName(request.getParameter("name"));
        hero.setDescription(request.getParameter("description"));

        service.updateHero(hero);

        return "redirect:/heros";
    }

    @GetMapping("deleteHero")
    public String deleteHero(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        service.deleteHeroById(id);

        return "redirect:/heros";
    }
}
