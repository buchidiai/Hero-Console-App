/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.service.ServiceLayer;
import java.util.ArrayList;
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
public class OrganizationController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("organization")
    public String getorganizationPage(Model model) {

//        List<Organization> organizations = service.getAllOrganizations();
        List<Hero> heros = service.getAllHeros();

//        model.addAttribute("organizations", organizations);
        model.addAttribute("heros", heros);
        model.addAttribute("errors", service.getOrganizationViolations());

        return "/organization/organization";
    }

    @GetMapping("organizations")
    public String getAllOrganizations(Model model) {

        List<Organization> organizations = service.getAllOrganizations();
        List<Hero> heros = service.getAllHeros();

        model.addAttribute("organizations", organizations);
        model.addAttribute("heros", heros);
        model.addAttribute("errors", service.getOrganizationViolations());

        return "/organization/organization";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {

        //organization parms from client
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");

        //heros ids
        String[] herosIds = request.getParameterValues("herosId");

        Organization organization = new Organization();

        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);

        List<Hero> heros = new ArrayList<>();

        if (herosIds != null) {
            for (String heroId : herosIds) {
                heros.add(service.getHeroById(Integer.parseInt(heroId)));
            }
        }

        //check if empty then add
        if (service.validateOrganization(organization).isEmpty()) {

            if (herosIds != null) {
                //set orgs
                organization.setHeros(heros);

            }

            //add organization
            service.addOrganization(organization);
            //add bridge table relationship

            if (herosIds != null) {
                service.insertOrganizationHero(organization);
            }

        }

        return "redirect:/organization/organization";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = service.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "/organization/editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result) {

        if (result.hasErrors()) {
            return "/organization/editOrganization";
        }

        service.updateOrganization(organization);
        return "redirect:/organization/organization";
    }

    @GetMapping("deleteOrganizationConfirm")
    public String deleteOrganizationConfirm(Integer id, Model model) {

        model.addAttribute("organizationId", id);

        return "/organization/deleteOrganizationConfirm";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {

        service.deleteOrganizationById(id);

        return "redirect:/organization/organization";
    }

}
