/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Character;
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
public class CharacterDaoDBTest {

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    CharacterDao characterDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

    SuperPower superPower = null;
    Character character1 = null;
    Character character2 = null;

    public CharacterDaoDBTest() {
    }

    @BeforeEach
    public void setUp() {
        List<Character> characters = characterDao.getAllCharacters();

        for (Character character : characters) {
            characterDao.deleteCharacterById(character.getId());
        }

        List<SuperPower> superPowers = superPowerDao.getAllSuperPowers();

        for (SuperPower superPower : superPowers) {
            superPowerDao.deleteSuperPowerById(superPower.getId());
        }

        //add super power
        superPower = new SuperPower();
        superPower.setSuperPower("invisibilty");
        superPower = superPowerDao.addSuperPower(superPower);

        //add hero
        character1 = new Character();
        character1.setName("Super Man");
        character1.setDescription("born on the planet Krypton and was given the name Kal-El.");
        character1.setSuperPower(String.valueOf(superPower.getId()));

        //add hero
        character2 = new Character();
        character2.setName("Bat Man");
        character2.setDescription("Rich guy who acts like a hero.");

    }

    @Test
    public void testAddCharacter() {
        //add hero
        character1 = characterDao.addCharacter(character1);

        //get hero
        Character foundCharacter = characterDao.getCharacterById(character1.getId());

        assertEquals(character1.getId(), foundCharacter.getId());
        assertEquals(character1.getDescription(), foundCharacter.getDescription());
        assertEquals(character1.getName(), foundCharacter.getName());
        assertEquals(character1.getSuperPower(), String.valueOf(superPower.getId()));

    }

    @Test
    public void testGetCharacter() {
        //add hero
        character1 = characterDao.addCharacter(character1);

        //get hero
        Character foundCharacter = characterDao.getCharacterById(character1.getId());

        assertEquals(character1.getId(), foundCharacter.getId());
        assertEquals(character1.getDescription(), foundCharacter.getDescription());
        assertEquals(character1.getName(), foundCharacter.getName());
        assertEquals(character1.getSuperPower(), String.valueOf(superPower.getId()));

    }

    @Test
    public void testGetAllCharacters() {
        //add heros
        character1 = characterDao.addCharacter(character1);

        character2 = characterDao.addCharacter(character2);

        List<Character> heros = characterDao.getAllCharacters();

        assertEquals(2, heros.size());

        assertEquals(character1.getId(), heros.get(0).getId());
        assertEquals(character1.getDescription(), heros.get(0).getDescription());
        assertEquals(character1.getName(), heros.get(0).getName());

        assertEquals(character2.getId(), heros.get(1).getId());
        assertEquals(character2.getDescription(), heros.get(1).getDescription());
        assertEquals(character2.getName(), heros.get(1).getName());
        assertNull(character2.getSuperPower());

    }

    @Test
    public void testUpdateCharacter() {
        //add hero
        character1 = characterDao.addCharacter(character1);

        Character foundCharacter = characterDao.getCharacterById(character1.getId());

        assertEquals(character1.getId(), foundCharacter.getId());
        assertEquals(character1.getDescription(), foundCharacter.getDescription());
        assertEquals(character1.getName(), foundCharacter.getName());
        assertEquals(character1.getSuperPower(), String.valueOf(superPower.getId()));

        character1.setName("Green Lantern");
        character1.setDescription("They fight evil with the aid of rings that grant them a variety of extraordinary powers");

        characterDao.updateCharacter(character1);

        Character updatedCharacter = characterDao.getCharacterById(character1.getId());

        assertNotEquals(foundCharacter.getName(), updatedCharacter.getName());
        assertNotEquals(foundCharacter.getDescription(), updatedCharacter.getDescription());

    }

    @Test
    public void testDeleteCharacter() {

        //add hero
        character1 = characterDao.addCharacter(character1);

        Character foundCharacter = characterDao.getCharacterById(character1.getId());

        assertEquals(character1.getId(), foundCharacter.getId());
        assertEquals(character1.getDescription(), foundCharacter.getDescription());
        assertEquals(character1.getName(), foundCharacter.getName());
        assertEquals(character1.getSuperPower(), String.valueOf(superPower.getId()));

        characterDao.deleteCharacterById(character1.getId());

        Character deletedCharacter = characterDao.getCharacterById(character1.getId());

        assertNull(deletedCharacter);

    }
}
