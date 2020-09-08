/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.entities.Organization;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author louie
 */
@Service
public class OrganizationSeviceLayerImpl implements OrganizationSeviceLayer {

    @Autowired
    private OrganizationDao organizationDao;

    private Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    private Set<ConstraintViolation<Organization>> organizationViolations = new HashSet<>();

    @Override
    public Organization getOrganizationById(int id) {
        return organizationDao.getOrganizationById(id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }

    @Override
    public void deleteOrganizationById(int id) {
        organizationDao.deleteOrganizationById(id);
    }

    @Override
    public Organization getOrganizationDetails(int id) {
        return organizationDao.getOrganizationDetails(id);
    }

    @Override
    public Set<ConstraintViolation<Organization>> validateOrganization(Organization organization) {
        return organizationViolations = validate.validate(organization);
    }

    @Override
    public Set<ConstraintViolation<Organization>> getOrganizationViolations() {
        return organizationViolations;
    }

}
