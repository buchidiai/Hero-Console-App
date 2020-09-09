/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sighting;
import com.sg.superherosighting.entities.SuperPower;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
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
public class SightingDaoDBTest {

    @Autowired
    private SuperPowerDao superPowerDao;

    @Autowired
    private HeroDao heroDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private SightingDao sightingDao;

    private SuperPower superPower = null;

    private Hero hero1 = null;

    private Hero hero2 = null;

    private Location location = null;

    private Sighting sighting = null;

    private Sighting sighting1 = null;

    public SightingDaoDBTest() {
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

        List<Location> locations = locationDao.getAllLocations();

        locations.forEach(location -> {
            locationDao.deleteLocationById(location.getId());
        });

        List<Sighting> sightings = sightingDao.getAllSightings();

        sightings.forEach(sighting -> {
            sightingDao.deleteSightingById(sighting.getId());
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
        hero1 = heroDao.addHero(hero1);

        //add hero
        hero2 = new Hero();
        hero2.setName("Bat Man");
        hero2.setDescription("Rich guy who acts like a hero.");
        hero2.setSuperPower_id(superPower.getId());
        hero2 = heroDao.addHero(hero2);

        //addd location
        location = new Location();
        location.setName("East La");
        location.setDescription("Shopping center");
        location.setAddress("12 east ln");
        location.setLatitude("45.5454");
        location.setLongitude("54.5454");
        location = locationDao.addLocation(location);

        //parse date
        LocalDateTime sightingDate = LocalDateTime.parse("2019-09-02T18:32");

        LocalDateTime sightingDate1 = LocalDateTime.parse("2020-10-03T18:32");

        sighting = new Sighting();
        sighting.setHeros(new ArrayList<Hero>(Arrays.asList(hero1)));
        sighting.setLocalDate(sightingDate);
        sighting.setLocationId(location.getId());

        sighting1 = new Sighting();
        sighting1.setHeros(new ArrayList<Hero>(Arrays.asList(hero2)));
        sighting1.setLocalDate(sightingDate1);
        sighting1.setLocationId(location.getId());

    }

    @Test
    public void testAddGetSighting() {

        //add sighting
        sighting = sightingDao.addSighting(sighting);

        //get sighting
        Sighting foundSighting = sightingDao.getSightingById(sighting.getId());

        assertEquals(sighting.getId(), foundSighting.getId());
        assertEquals(sighting.getLocationId(), foundSighting.getLocationId());
        assertEquals(sighting.getLocalDate(), foundSighting.getLocalDate());
    }

    @Test
    public void testGetAllSightings() {

        //add sighting
        sighting = sightingDao.addSighting(sighting);

        sighting1 = sightingDao.addSighting(sighting1);

        List<Sighting> allSighting = sightingDao.getAllSightings();

        Sighting retrivedSighting1 = allSighting.get(0);
        Sighting retrivedSighting2 = allSighting.get(1);

        assertEquals(2, allSighting.size());

        assertEquals(sighting.getId(), retrivedSighting2.getId());
        assertEquals(sighting.getLocationId(), retrivedSighting2.getLocationId());
        assertEquals(sighting.getLocalDate(), retrivedSighting2.getLocalDate());

        assertEquals(sighting1.getId(), retrivedSighting1.getId());
        assertEquals(sighting1.getLocationId(), retrivedSighting1.getLocationId());
        assertEquals(sighting1.getLocalDate(), retrivedSighting1.getLocalDate());

    }

    @Test
    public void testDeleteSightings() {

        //add sighting
        sighting = sightingDao.addSighting(sighting);

        List<Sighting> allSighting = sightingDao.getAllSightings();

        Sighting retrivedSighting1 = allSighting.get(0);

        assertEquals(1, allSighting.size());

        sightingDao.deleteSightingById(retrivedSighting1.getId());

        assertTrue(sightingDao.getAllSightings().isEmpty());

    }

    @Test
    public void testUpdateSightings() {

        //add sighting
        sighting = sightingDao.addSighting(sighting);

        Sighting retrivedSighting = sightingDao.getSightingById(sighting.getId());

        retrivedSighting.setHeroId(hero2.getId());

        sightingDao.updateSighting(retrivedSighting);

        Sighting updatedSighting = sightingDao.getSightingById(sighting.getId());

        assertTrue(sighting.getHeros().get(0).getId() != updatedSighting.getHeroId());
        assertNotEquals(sighting.getHeros(), updatedSighting.getHero());
    }

}
