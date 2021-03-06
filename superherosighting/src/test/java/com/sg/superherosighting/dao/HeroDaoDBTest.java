/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.SuperPower;
import java.util.ArrayList;
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
    private SuperPowerDao superPowerDao;

    @Autowired
    private HeroDao heroDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private OrganizationDao organizationDao;

    private SuperPower superPower = null;
    private Hero hero1 = null;
    private Hero hero2 = null;
    private Organization organization1 = null;
    private Location location = null;

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

        List<Location> locations = locationDao.getAllLocations();

        locations.forEach(location -> {
            locationDao.deleteLocationById(location.getId());
        });

        //add super power
        superPower = new SuperPower();
        superPower.setName("invisibilty");
        superPower = superPowerDao.addSuperPower(superPower);

        //add hero
        hero1 = new Hero();
        hero1.setName("Super Man");
        hero1.setDescription("born on the planet Krypton and was given the name Kal-El.");
        hero1.setSuperPower_id(superPower.getId());

        //add hero
        hero2 = new Hero();
        hero2.setName("Bat Man");
        hero2.setDescription("Rich guy who acts like a hero.");

        organization1 = new Organization();
        organization1.setName("Justice League");
        organization1.setDescription("Org for best Heros");
        organization1.setAddress("Space");

        location = new Location();
        location.setName("East La");
        location.setDescription("Shopping center");
        location.setAddress("12 east ln");
        location.setLatitude("45.5454");
        location.setLongitude("54.5454");

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
//

    @Test
    public void testGetAllHeros() {

        hero1.setSuperPower_id(superPower.getId());
        //add heros
        hero1 = heroDao.addHero(hero1);

        hero2.setSuperPower_id(superPower.getId());

        hero2 = heroDao.addHero(hero2);

        List<Hero> heros = heroDao.getAllHeros();

        assertEquals(2, heros.size());

        assertEquals(hero2.getId(), heros.get(0).getId());
        assertEquals(hero2.getDescription(), heros.get(0).getDescription());
        assertEquals(hero2.getName(), heros.get(0).getName());
        assertNull(hero2.getSuperPower());

        assertEquals(hero1.getId(), heros.get(1).getId());
        assertEquals(hero1.getDescription(), heros.get(1).getDescription());
        assertEquals(hero1.getName(), heros.get(1).getName());

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

    @Test
    public void testAddOrganizationToHero() {

        //add org
        organization1 = organizationDao.addOrganization(organization1);

        //add hero
        hero1 = heroDao.addHero(hero1);

        //get org
        organization1 = organizationDao.getOrganizationById(organization1.getId());

        List<Organization> organizations = new ArrayList<>();
        organizations.add(organization1);

        //set org to hero object
        hero1.setOrganizations(organizations);

        //add org - hero to bridge table
        heroDao.updateHero(hero1);

        Hero heroFound = heroDao.getHeroDetails(hero1.getId());

        assertEquals(organizations, heroFound.getOrganizations());

    }

    @Test
    public void testAddLocationToHero() {

        //add location
        location = locationDao.addLocation(location);

        //add hero
        hero1 = heroDao.addHero(hero1);

        //get org
        location = locationDao.getLocationById(location.getId());

        List<Location> locations = new ArrayList<>();
        locations.add(location);

        //set location to hero object
        hero1.setLocations(locations);

        //add org - hero to bridge table
        heroDao.updateHero(hero1);

        Hero heroFound = heroDao.getHeroDetails(hero1.getId());

        assertEquals(locations.get(0).getId(), heroFound.getLocations().get(0).getId());

    }

    @Test
    public void testHeroSuperPower() {

        //add super power to hero
        hero1.setSuperPower_id(superPower.getId());
        //add heros
        hero1 = heroDao.addHero(hero1);
        //get hero
        hero1 = heroDao.getHeroById(hero1.getId());

        assertEquals(superPower.getName(), hero1.getSuperPower());

    }
}
