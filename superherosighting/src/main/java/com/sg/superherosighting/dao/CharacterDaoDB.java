/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Character;
import com.sg.superherosighting.entities.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import javax.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

/**
 *
 * @author louie
 */
@Repository
public class CharacterDaoDB implements CharacterDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Character getCharacterById(int id) {
        try {

            final String SELECT_CHARACTER_BY_ID = "SELECT * FROM character WHERE id = ?";
            Character character = jdbc.queryForObject(SELECT_CHARACTER_BY_ID, new CharacterMapper(), id);

            final String SELECT_SUPERPOWER_BY_ID = "SELECT * FROM superPower WHERE id = ?";

            SuperPower superPower = jdbc.queryForObject(SELECT_SUPERPOWER_BY_ID, new SuperPowerMapper(), character.getSuperPower());

            character.setSuperPower(superPower.getSuperPower());

            return character;
        } catch (DataAccessException ex) {

            return null;
        }
    }

    @Override
    public List<Character> getAllCharacters() {
        final String SELECT_ALL_CHARACTERS = "SELECT * FROM character";
        return jdbc.query(SELECT_ALL_CHARACTERS, new CharacterMapper());
    }

    @Override
    @Transactional
    public Character addCharacter(Character character) {

        final String INSERT_CHARACTER = "INSERT INTO character(name, description, superPower_id) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_CHARACTER,
                character.getName(),
                character.getDescription(), character.getSuperPower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        character.setId(newId);

        return character;
    }

    @Override
    public void updateCharacter(Character character) {
        final String UPDATE_CHARACTER = "UPDATE character SET name = ?, description = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_CHARACTER,
                character.getName(),
                character.getDescription(),
                character.getId());
    }

    @Override
    @Transactional
    public void deleteCharacterById(int id) {
//        final String DELETE_ORGANIZATION_CHARACTER = "DELETE FROM hero_has_organization WHERE hero_id = ?";
//        jdbc.update(DELETE_ORGANIZATION_CHARACTER, id);
//
//        final String DELETE_LOCATION_CHARACTER = "DELETE FROM hero_has_location WHERE organization_id = ?";
//        jdbc.update(DELETE_LOCATION_CHARACTER, id);

        final String DELETE_CHARACTER = "DELETE FROM character WHERE id = ?";
        jdbc.update(DELETE_CHARACTER, id);
    }

    public static final class CharacterMapper implements RowMapper<Character> {

        @Override
        public Character mapRow(ResultSet rs, int index) throws SQLException {

            Character character = new Character();
            character.setId(rs.getInt("id"));
            character.setName(rs.getString("name"));
            character.setDescription(rs.getString("description"));
            character.setSuperPower(rs.getString("superPower_id"));

            return character;
        }
    }

    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {

            SuperPower superCharacter = new SuperPower();
            superCharacter.setId(rs.getInt("id"));
            superCharacter.setSuperPower(rs.getString("name"));

            return superCharacter;
        }
    }

}
