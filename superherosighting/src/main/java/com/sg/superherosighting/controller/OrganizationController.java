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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author louie
 */
@Controller
public class OrganizationController {

    @Autowired
    private ServiceLayer service;

    @GetMapping("organization")
    public String getOrganizationPage(Model model) {

        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);
        model.addAttribute("errors", service.getOrganizationViolations());

        return "/organization/organization";
    }

    @GetMapping("allOrganizations")
    public String getAllOrganizations(Model model) {

        List<Organization> organizations = service.getAllOrganizations();

        model.addAttribute("organizations", organizations);

        return "/organization/listOrganizations";
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

        return "redirect:allOrganizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer organizationId, Model model) {

        Organization organization = service.getOrganizationById(organizationId);

        model.addAttribute("organization", organization);
        return "/organization/editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            return "/organization/editOrganization";
        }

        service.updateOrganization(organization);

        redirectAttributes.addAttribute("organizationId", organization.getId());

        return "redirect:organizationDetails";
    }

    @GetMapping("deleteOrganizationConfirm")
    public String deleteOrganizationConfirm(Integer organizationId, Model model) {

        model.addAttribute("organizationId", organizationId);

        return "/organization/deleteOrganizationConfirm";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer organizationId) {

        service.deleteOrganizationById(organizationId);

        return "redirect:allOrganizations";
    }

    @GetMapping("organizationDetails")
    public String organizationDetails(Integer organizationId, Model model) {

        Organization organization = service.getOrganizationDetails(organizationId);

        model.addAttribute("organizationDetails", organization);

        return "/organization/organizationDetails";
    }

    @GetMapping("editOrganizationHero")
    public String editOrganizationHero(Integer organizationId, Integer heroId, Model model) {

        List<Hero> organizationHeros = service.getOrganizationDetails(organizationId).getHeros();

        List<Hero> allHeros = service.getAllHeros();

        organizationHeros.stream().filter(h -> (allHeros.contains(h))).filter(h -> (h.getId() != heroId)).forEachOrdered(h -> {
            allHeros.remove(h);
        });

        model.addAttribute("heros", allHeros);
        model.addAttribute("organizationId", organizationId);
        model.addAttribute("heroId", heroId);

        return "/organization/editOrganizationHero";
    }

    @PostMapping("editOrganizationHero")
    public String performEditOrganizationHero(Model model, Integer newHeroId, Integer heroId, Integer organizationId,
            RedirectAttributes redirectAttributes) {

        Organization organization = service.getOrganizationById(organizationId);

        Hero hero = service.getHeroById(newHeroId);

        service.updateOrganizationHero(hero, organization, heroId);

        redirectAttributes.addAttribute("organizationId", organization.getId());

        return "redirect:organizationDetails";
    }

    @GetMapping("deleteOrganizationHeroConfirm")
    public String deleteOrganizationHeroConfirm(Integer heroId, Integer organizationId, Model model) {

        model.addAttribute("heroId", heroId);
        model.addAttribute("organizationId", organizationId);

        return "/organization/deleteOrganizationHeroConfirm";
    }

    @GetMapping("deleteOrganizationHero")
    public String deleteOrganizationHero(Integer heroId, Integer organizationId, RedirectAttributes redirectAttributes) {

        Hero hero = service.getHeroById(heroId);

        Organization organization = service.getOrganizationById(organizationId);

        service.deleteHeroOrganization(hero, organization);

        redirectAttributes.addAttribute("organizationId", organization.getId());

        return "redirect:organizationDetails";
    }
}
