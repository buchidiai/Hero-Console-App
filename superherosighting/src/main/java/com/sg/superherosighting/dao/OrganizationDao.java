/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Organization;
import java.util.List;

/**
 *
 * @author louie
 */
public interface OrganizationDao {

    Organization getOrganizationById(int id);

    void insertOrganizationHero(Organization organization);

    void updateOrganizationHero(Hero hero, Organization organization, int originalId);

    Organization getOrganizationDetails(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);

}
