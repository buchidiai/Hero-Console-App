/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Character;
import java.util.List;

/**
 *
 * @author louie
 */
public interface CharacterDao {

    Character getCharacterById(int id);

    List<Character> getAllCharacters();

    Character addCharacter(Character character);

    void updateCharacter(Character character);

    void deleteCharacterById(int id);

}
