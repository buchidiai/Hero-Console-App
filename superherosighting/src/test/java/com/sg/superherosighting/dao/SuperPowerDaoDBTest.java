/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
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
public class SuperPowerDaoDBTest {

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao heroDao;

    SuperPower superPower1 = null;
    SuperPower superPower2 = null;

    Hero hero1 = null;

    public SuperPowerDaoDBTest() {
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

        //add super power
        superPower1 = new SuperPower();
        superPower1.setName("Super Strength");

        //add super power
        superPower2 = new SuperPower();
        superPower2.setName("Xray");

        //add hero
        hero1 = new Hero();
        hero1.setName("Super Man");
        hero1.setDescription("born on the planet Krypton and was given the name Kal-El.");

    }

    @Test
    public void testAddSuperPower() {
        //add superPower
        superPower1 = superPowerDao.addSuperPower(superPower1);

        //get superPower
        SuperPower foundSuperPower = superPowerDao.getSuperPowerById(superPower1.getId());

        assertEquals(superPower1, foundSuperPower);

    }

    @Test
    public void testAddSuperPowerTohero() {
        //add superPower
        superPower1 = superPowerDao.addSuperPower(superPower1);

        hero1.setSuperPower_id(superPower1.getId());

        //get superPower
        SuperPower foundSuperPower = superPowerDao.getSuperPowerById(superPower1.getId());

        assertEquals(superPower1, foundSuperPower);

    }

    @Test
    public void testGetSuperPower() {
        //add superPower
        superPower1 = superPowerDao.addSuperPower(superPower1);

        //get superPower
        SuperPower foundSuperPower = superPowerDao.getSuperPowerById(superPower1.getId());

        assertEquals(superPower1, foundSuperPower);
    }

    @Test
    public void testGetAllSuperPowers() {
        //add superPowers
        superPower1 = superPowerDao.addSuperPower(superPower1);

        superPower2 = superPowerDao.addSuperPower(superPower2);

        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();

        assertEquals(2, superPowers.size());

        assertTrue(superPowers.contains(superPower1));
        assertTrue(superPowers.contains(superPower2));

    }

    @Test
    public void testUpdateSuperPower() {
        //add superPower
        superPower1 = superPowerDao.addSuperPower(superPower1);

        SuperPower foundSuperPower = superPowerDao.getSuperPowerById(superPower1.getId());

        assertEquals(superPower1, foundSuperPower);

        superPower1.setName("laser beams");

        superPowerDao.updateSuperPower(superPower1);

        SuperPower updatedSuperPower = superPowerDao.getSuperPowerById(superPower1.getId());

        assertNotEquals(foundSuperPower, updatedSuperPower);

    }

    @Test
    public void testDeleteSuperPower() {

        //add superPower
        superPower1 = superPowerDao.addSuperPower(superPower1);

        SuperPower foundSuperPower = superPowerDao.getSuperPowerById(superPower1.getId());

        assertEquals(superPower1, foundSuperPower);

        superPowerDao.deleteSuperPowerById(superPower1.getId());

        SuperPower deletedSuperPower = superPowerDao.getSuperPowerById(superPower1.getId());

        assertNull(deletedSuperPower);

    }
}
