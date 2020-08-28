/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
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
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    @Transactional
    public Hero getHeroById(int id) {
        try {

            final String SELECT_HERO_BY_ID = "SELECT * FROM hero WHERE id = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);

            final String SELECT_SUPERPOWER_BY_ID = "SELECT * FROM superPower WHERE id = ?";

            SuperPower superPower = jdbc.queryForObject(SELECT_SUPERPOWER_BY_ID, new SuperPowerMapper(), hero.getSuperPower());

            hero.setSuperPower(superPower.getSuperPower());

            return hero;
        } catch (DataAccessException ex) {

            return null;
        }
    }

    @Override
    public List<Hero> getAllHeros() {
        final String SELECT_ALL_HEROS = "SELECT * FROM hero";
        return jdbc.query(SELECT_ALL_HEROS, new HeroMapper());
    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {

        final String INSERT_HERO = "INSERT INTO hero(name, description, superPower_id) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(), hero.getSuperPower());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);

        return hero;
    }

    @Override
    public void updateHero(Hero hero) {
        final String UPDATE_HERO = "UPDATE hero SET name = ?, description = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getId());
    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
//        final String DELETE_ORGANIZATION_HERO = "DELETE FROM hero_has_organization WHERE hero_id = ?";
//        jdbc.update(DELETE_ORGANIZATION_HERO, id);
//
//        final String DELETE_LOCATION_HERO = "DELETE FROM hero_has_location WHERE organization_id = ?";
//        jdbc.update(DELETE_LOCATION_HERO, id);

        final String DELETE_HERO = "DELETE FROM hero WHERE id = ?";
        jdbc.update(DELETE_HERO, id);
    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {

            Hero hero = new Hero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setSuperPower(rs.getString("superPower_id"));

            return hero;
        }
    }

    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {

            SuperPower superHero = new SuperPower();
            superHero.setId(rs.getInt("id"));
            superHero.setSuperPower(rs.getString("name"));

            return superHero;
        }
    }

}
