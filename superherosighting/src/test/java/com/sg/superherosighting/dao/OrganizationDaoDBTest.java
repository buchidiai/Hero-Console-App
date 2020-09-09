/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.SuperPower;
import java.util.ArrayList;
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
    private SuperPowerDao superPowerDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private HeroDao heroDao;

    private Organization organization1 = null;

    private Organization organization2 = null;

    private Hero hero1 = null;

    private Hero hero2 = null;

    private SuperPower superPower = null;

    private SuperPower superPower1 = null;

    public OrganizationDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {

        List<Hero> heros = heroDao.getAllHeros();

        heros.forEach(hero -> {
            heroDao.deleteHeroById(hero.getId());
        });

        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();

        superPowers.forEach(superPower -> {
            superPowerDao.deleteSuperPowerById(superPower.getId());
        });

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

        //add super power
        superPower = new SuperPower();
        superPower.setName("Xray");
        superPower = superPowerDao.addSuperPower(superPower);

        superPower1 = new SuperPower();
        superPower1.setName("Strength");
        superPower1 = superPowerDao.addSuperPower(superPower1);

        //add hero
        hero1 = new Hero();
        hero1.setName("Super Man");
        hero1.setDescription("born on the planet Krypton and was given the name Kal-El.");
        hero1.setSuperPower_id(superPower.getId());

        //add hero
        hero2 = new Hero();
        hero2.setName("Iron Man");
        hero2.setDescription("tech genius.");
        hero2.setSuperPower_id(superPower1.getId());

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

    @Test
    public void testAddHeroToOrganization() {

        hero1 = heroDao.addHero(hero1);

        hero2 = heroDao.addHero(hero2);

        List<Hero> heros = new ArrayList<>();
        heros.add(hero1);
        heros.add(hero2);

        //add organization
        organization1 = organizationDao.addOrganization(organization1);

        organization1.setHeros(heros);

        organizationDao.updateOrganization(organization1);

        Organization organization = organizationDao.getOrganizationDetails(organization1.getId());

        List<Hero> addedHeros = organization.getHeros();
        assertEquals(2, addedHeros.size());

    }

}
