/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Organization;
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
public class OrganizationController {

    @Autowired
    ServiceLayer service;

    Set<ConstraintViolation<Organization>> organizationViolations = new HashSet<>();

    @GetMapping("organizations")
    public String getAllOrganizations(Model model) {

        List<Organization> organizations = service.getAllOrganizations();

        model.addAttribute("organizations", organizations);
        model.addAttribute("errors", organizationViolations);

        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(HttpServletRequest request) {

        //organization parms from client
        String name = request.getParameter("name");
        String description = request.getParameter("description");
        String address = request.getParameter("address");

        Organization organization = new Organization();

        Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

        organization.setName(name);
        organization.setDescription(description);
        organization.setAddress(address);

        //check if valid
        organizationViolations = validate.validate(organization);

        //check if empty then add
        if (organizationViolations.isEmpty()) {
            //add organization
            service.addOrganization(organization);
        }

        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(Integer id, Model model) {
        Organization organization = service.getOrganizationById(id);
        model.addAttribute("organization", organization);
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization organization, BindingResult result) {

        if (result.hasErrors()) {
            return "editOrganization";
        }

        service.updateOrganization(organization);
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganizationConfirm")
    public String deleteOrganizationConfirm(Integer id, Model model) {

        model.addAttribute("organizationId", id);

        return "deleteOrganizationConfirm";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(Integer id) {

        service.deleteOrganizationById(id);

        return "redirect:/organizations";
    }
}
