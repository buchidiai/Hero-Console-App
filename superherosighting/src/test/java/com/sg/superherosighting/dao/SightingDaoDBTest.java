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
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
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

        List<Hero> herosAtSighting = new ArrayList<>();

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

        //add heros to arraylist
        herosAtSighting.add(hero1);
        herosAtSighting.add(hero2);

        //addd location
        location = new Location();
        location.setName("East La");
        location.setDescription("Shopping center");
        location.setAddress("12 east ln");
        location.setLatitude("45.5454");
        location.setLongitude("54.5454");
        location = locationDao.addLocation(location);

        //parse date
        LocalDateTime sightingDate = LocalDateTime.parse("2020-09-02T18:32");

        LocalDateTime sightingDate1 = LocalDateTime.parse("2020-10-03T18:32");

        sighting = new Sighting();
        sighting.setHeros(herosAtSighting);
        sighting.setLocalDate(sightingDate);
        sighting.setLocationId(location.getId());

        sighting1 = new Sighting();
        sighting1.setHeros(herosAtSighting);
        sighting1.setLocalDate(sightingDate1);
        sighting1.setLocationId(location.getId());

    }

//    @Test
//    public void testAddGetSighting() {
//
//        //add sighting
//        sighting = sightingDao.addSighting(sighting);
//
//        //get sighting
//        Sighting foundSighting = sightingDao.getSightingById(sighting.getId());
//
//        assertEquals(sighting.getId(), foundSighting.getId());
//        assertEquals(sighting.getLocationId(), foundSighting.getLocationId());
//        assertEquals(sighting.getLocalDate(), foundSighting.getLocalDate());
//    }
    @Test
    public void testGetAllSightings() {

        //add sighting
        sighting = sightingDao.addSighting(sighting);

        sighting1 = sightingDao.addSighting(sighting1);

        List<Sighting> sighting = sightingDao.getAllSightings();

        assertEquals(4, sighting.size());

    }

}
