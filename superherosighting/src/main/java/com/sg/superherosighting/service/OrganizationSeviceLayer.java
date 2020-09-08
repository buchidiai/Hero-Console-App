/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.entities.Organization;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author louie
 */
public interface OrganizationSeviceLayer {

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    Organization getOrganizationById(int id);

    Organization getOrganizationDetails(int id);

    void deleteOrganizationById(int id);

    void updateOrganization(Organization organization);

    Set<ConstraintViolation<Organization>> validateOrganization(Organization organization);

    Set<ConstraintViolation<Organization>> getOrganizationViolations();

}
