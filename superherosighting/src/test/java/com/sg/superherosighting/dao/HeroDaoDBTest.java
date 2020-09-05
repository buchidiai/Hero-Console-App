/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.SuperPower;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
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
public class HeroDaoDBTest {

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    SuperPower superPower = null;
    Hero hero1 = null;
    Hero hero2 = null;
    Organization organization1 = null;

    public HeroDaoDBTest() {
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

        organizations.forEach(organization -> {
            organizationDao.deleteOrganizationById(organization.getId());
        });

        //add super power
        superPower = new SuperPower();
        superPower.setName("invisibilty");
        superPower = superPowerDao.addSuperPower(superPower);

        //add hero
        hero1 = new Hero();
        hero1.setName("Super Man");
        hero1.setDescription("born on the planet Krypton and was given the name Kal-El.");
        hero1.setSuperPower(superPower.getName());

        //add hero
        hero2 = new Hero();
        hero2.setName("Bat Man");
        hero2.setDescription("Rich guy who acts like a hero.");

        organization1 = new Organization();
        organization1.setName("Justice League");
        organization1.setDescription("Org for best Heros");
        organization1.setAddress("Space");

    }

    @Test
    public void testAddHero() {
        //add hero
        hero1 = heroDao.addHero(hero1);

        //get hero
        Hero foundHero = heroDao.getHeroById(hero1.getId());

        assertEquals(hero1.getId(), foundHero.getId());
        assertEquals(hero1.getDescription(), foundHero.getDescription());
        assertEquals(hero1.getName(), foundHero.getName());
        assertEquals(foundHero.getSuperPower(), superPower.getName());

    }

    @Test
    public void testGetHero() {
        //add hero
        hero1 = heroDao.addHero(hero1);

        //get hero
        Hero foundHero = heroDao.getHeroById(hero1.getId());

        assertEquals(hero1.getId(), foundHero.getId());
        assertEquals(hero1.getDescription(), foundHero.getDescription());
        assertEquals(hero1.getName(), foundHero.getName());
        assertEquals(foundHero.getSuperPower(), superPower.getName());

    }

    @Test
    public void testGetAllHeros() {
        //add heros
        hero1 = heroDao.addHero(hero1);

        hero2 = heroDao.addHero(hero2);

        List<Hero> heros = heroDao.getAllHeros();

        assertEquals(2, heros.size());

        assertEquals(hero1.getId(), heros.get(0).getId());
        assertEquals(hero1.getDescription(), heros.get(0).getDescription());
        assertEquals(hero1.getName(), heros.get(0).getName());

        assertEquals(hero2.getId(), heros.get(1).getId());
        assertEquals(hero2.getDescription(), heros.get(1).getDescription());
        assertEquals(hero2.getName(), heros.get(1).getName());
        assertNull(hero2.getSuperPower());

    }

    @Test
    public void testUpdateHero() {
        //add hero
        hero1 = heroDao.addHero(hero1);

        Hero foundHero = heroDao.getHeroById(hero1.getId());

        assertEquals(hero1.getId(), foundHero.getId());
        assertEquals(hero1.getDescription(), foundHero.getDescription());
        assertEquals(hero1.getName(), foundHero.getName());
        assertEquals(foundHero.getSuperPower(), superPower.getName());

        hero1.setName("Green Lantern");
        hero1.setDescription("They fight evil with the aid of rings that grant them a variety of extraordinary powers");

        heroDao.updateHero(hero1);

        Hero updatedHero = heroDao.getHeroById(hero1.getId());

        assertNotEquals(foundHero.getName(), updatedHero.getName());
        assertNotEquals(foundHero.getDescription(), updatedHero.getDescription());

    }

    @Test
    public void testDeleteHero() {

        //add hero
        hero1 = heroDao.addHero(hero1);

        Hero foundHero = heroDao.getHeroById(hero1.getId());

        assertEquals(hero1.getId(), foundHero.getId());
        assertEquals(hero1.getDescription(), foundHero.getDescription());
        assertEquals(hero1.getName(), foundHero.getName());
        assertEquals(foundHero.getSuperPower(), superPower.getName());

        heroDao.deleteHeroById(hero1.getId());

        Hero deletedHero = heroDao.getHeroById(hero1.getId());

        assertNull(deletedHero);

    }

//    @Test
//    public void testInsertHeroToOrganization() {
//
//        //add organization
//        organization1 = organizationDao.addOrganization(organization1);
//
//        Organization foundOrganization = organizationDao.getOrganizationById(organization1.getId());
//
//        assertEquals(hero1.getId(), foundHero.getId());
//        assertEquals(hero1.getDescription(), foundHero.getDescription());
//        assertEquals(hero1.getName(), foundHero.getName());
//        assertEquals(foundHero.getSuperPower(), superPower.getName());
//
//        heroDao.deleteHeroById(hero1.getId());
//
//        Hero deletedHero = heroDao.getHeroById(hero1.getId());
//
//        assertNull(deletedHero);
//
//    }
}
