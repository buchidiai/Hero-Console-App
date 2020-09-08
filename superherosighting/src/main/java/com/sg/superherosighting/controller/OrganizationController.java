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
    public String addOrganization(@Valid Organization organization, BindingResult result, HttpServletRequest request) {

        if (result.hasErrors()) {
            service.validateOrganization(organization);
            return "redirect:organization";
        }

        service.addOrganization(organization);

        return "redirect:allOrganizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer organizationId, Model model) {

        Organization organization = service.getOrganizationById(organizationId);
        List<Hero> heros = service.getAllHeros();

        model.addAttribute("heros", heros);
        model.addAttribute("organization", organization);
        model.addAttribute("errors", service.getSuperPowerViolations());

        return "/organization/editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request) {

        if (result.hasErrors()) {
            service.validateOrganization(organization);
            redirectAttributes.addAttribute("organizationId", organization.getId());
            return "/organization/editOrganization";
        }

        //heros ids
        String[] heroIds = request.getParameterValues("heroIds");

        //get and set heros
        getAndSetHeros(heroIds, organization);

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

    private void getAndSetHeros(String[] heroIds, Organization organization) {

        List<Hero> heros = new ArrayList<>();

        if (heroIds != null) {

            //get heros
            for (String heroId : heroIds) {

                heros.add(service.getHeroById(Integer.parseInt(heroId)));
            }
            //set orgs
            organization.setHeros(heros);
        }

    }

}
