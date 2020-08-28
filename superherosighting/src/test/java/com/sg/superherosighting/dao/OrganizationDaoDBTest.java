/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Organization;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author louie
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrganizationDaoDBTest {

    @Autowired
    OrganizationDao organizationDao;

    @Autowired
    SuperPowerDao superPowerDao;

    Organization organization1 = null;

    Organization organization2 = null;

    public OrganizationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {

        List<Organization> organizations = organizationDao.getAllOrganizations();

        for (Organization organization : organizations) {
            organizationDao.deleteOrganizationById(organization.getId());
        }

        //add organization
        organization1 = new Organization();
        organization1.setName("Might Hero Acedmey");
        organization1.setDescription("Organizations for heros");
        organization1.setAddress("19876 hero plaza, Mesquite tx 72334");

        //add organization 2
        organization2 = new Organization();
        organization2.setName("Evil Villian Club");
        organization2.setDescription("Haven for villans");
        organization2.setAddress("20000 evil dr, California, La 90201");

    }

    @Test
    public void testAddOrganization() {
        //add organization
        organization1 = organizationDao.addOrganization(organization1);

        //get organization
        Organization foundOrganizations = organizationDao.getOrganizationById(organization1.getId());

        assertEquals(organization1, foundOrganizations);

    }

    @Test
    public void testGetOrganization() {
        //add organization
        organization1 = organizationDao.addOrganization(organization1);

        //get organization
        Organization foundOrganization = organizationDao.getOrganizationById(organization1.getId());

        assertEquals(organization1, foundOrganization);
    }

    @Test
    public void testGetAllOrganizations() {
        //add organizations
        organization1 = organizationDao.addOrganization(organization1);

        organization2 = organizationDao.addOrganization(organization2);

        List<Organization> organizations = organizationDao.getAllOrganizations();

        assertEquals(2, organizations.size());

        assertTrue(organizations.contains(organization1));
        assertTrue(organizations.contains(organization2));

    }

    @Test
    public void testUpdateOrganization() {
        //add organization
        organization1 = organizationDao.addOrganization(organization1);

        Organization foundOrganization = organizationDao.getOrganizationById(organization1.getId());

        assertEquals(organization1, foundOrganization);

        organization1.setDescription("Org for Top heros of the world");

        organizationDao.updateOrganization(organization1);

        Organization updatedOrganization = organizationDao.getOrganizationById(organization1.getId());

        assertNotEquals(foundOrganization, updatedOrganization);

    }

    @Test
    public void testDeleteOrganization() {

        //add organization
        organization1 = organizationDao.addOrganization(organization1);

        Organization foundOrganization = organizationDao.getOrganizationById(organization1.getId());

        assertEquals(organization1, foundOrganization);

        organizationDao.deleteOrganizationById(organization1.getId());

        Organization deletedOrganization = organizationDao.getOrganizationById(organization1.getId());

        assertNull(deletedOrganization);

    }

}
