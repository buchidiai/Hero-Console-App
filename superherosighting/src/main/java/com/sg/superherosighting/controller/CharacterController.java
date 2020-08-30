/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Character;
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
public class CharacterController {

    @Autowired
    ServiceLayer service;

    @GetMapping("characters")
    public String getAllCharacters(Model model) {

        List<Character> characters = service.getAllCharacters();

        model.addAttribute("characters", characters);

        return "characters";
    }

    @PostMapping("addCharacter")
    public String addCharacter(HttpServletRequest request) {

        //super power params
        String super_Power = request.getParameter("superPower");

        //character parms
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        SuperPower superPower = new SuperPower();
        superPower.setSuperPower(super_Power);

        //add super power
        superPower = service.addSuperPower(superPower);

//        System.out.println("superPower after add  " + superPower.toString());
        Character character = new Character();

        character.setName(name);
        character.setDescription(description);
        character.setSuperPower(String.valueOf(superPower.getId()));

//        System.out.println("character before add  " + character.toString());
        //add charater
        service.addCharacter(character);

        return "redirect:/characters";
    }
}
