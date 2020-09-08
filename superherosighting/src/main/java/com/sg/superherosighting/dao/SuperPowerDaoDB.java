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
public class SuperPowerDaoDB implements SuperPowerDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public SuperPower getSuperPowerById(int id) {

        try {
            final String SELECT_SUPERPOWER_BY_ID = "SELECT * FROM superPower WHERE id = ?";
            SuperPower superPower = jdbc.queryForObject(SELECT_SUPERPOWER_BY_ID, new SuperPowerMapper(), id);

            return superPower;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<SuperPower> getSuperPowerByName(String name) {

        try {
            final String FIND_SUPER_POWER = "SELECT * FROM superPower WHERE name = ?";

            return jdbc.query(FIND_SUPER_POWER, new SuperPowerMapper(), name);

        } catch (DataAccessException ex) {
            return null;
        }

    }

    @Override
    public void insertSuperPowerHero(SuperPower superPower) {
        final String INSERT_SUPERPOWER_HERO = "UPDATE hero SET superPower_id = ?  WHERE id = ?";

        jdbc.update(INSERT_SUPERPOWER_HERO,
                superPower.getId(),
                superPower.getHero().getId());
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        final String SELECT_ALL_SUPERPOWERS = "SELECT * FROM superPower";
        return jdbc.query(SELECT_ALL_SUPERPOWERS, new SuperPowerMapper());
    }

    @Override
    @Transactional
    public SuperPower addSuperPower(SuperPower superPower) {

        final String INSERT_SUPERPOWER = "INSERT INTO superPower(name)"
                + "VALUES(?)";

        jdbc.update(INSERT_SUPERPOWER,
                superPower.getName().toLowerCase());
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        superPower.setId(newId);

        return superPower;

    }

    @Override
    public void updateSuperPower(SuperPower superPower) {

        try {

            final String UPDATE_SUPERPOWER = "UPDATE superPower SET name = ? "
                    + "WHERE id = ?";

            jdbc.update(UPDATE_SUPERPOWER,
                    superPower.getName().toLowerCase(),
                    superPower.getId());

            updateHerosSuperPower(superPower);

        } catch (DataAccessException ex) {

        }

    }

    private void updateHerosSuperPower(SuperPower superPower) {

        if (superPower.getHeros() != null) {

            final String UPDATE_SUPER_POWER_HERO = "UPDATE hero SET superPower_id = ? "
                    + "WHERE id = ?";

            for (Hero hero : superPower.getHeros()) {
                jdbc.update(UPDATE_SUPER_POWER_HERO,
                        superPower.getId(),
                        hero.getId());
            }
        }
    }

    @Override
    @Transactional
    public void deleteSuperPowerById(int id) {

        final String DELETE_SUPERPOWER = "DELETE FROM superPower WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
    }

    @Override
    public List<Hero> getSuperPowerDetails(int superPowerId) {

        final String GET_ALL_SUPER_POWER_HERO = "SELECT h.id, h.name, h.description, s.name FROM  hero h JOIN superPower s On h.superPower_id = s.id WHERE superPower_id = ?";

        List<Hero> heros = jdbc.query(GET_ALL_SUPER_POWER_HERO, new HeroDaoDB.HeroMapper(), superPowerId);

        if (heros.isEmpty()) {
            return null;

        } else {
            return heros;
        }
    }

    @Override
    @Transactional
    public void updateSuperPowerHero(Hero hero, int oldHeroId) {

        //create empty super power placeholder for hero who lost power
        final String INSERT_SUPERPOWER = "INSERT INTO superPower(name) VALUES(?)";
        jdbc.update(INSERT_SUPERPOWER, "none");
        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        //update hero with last power to
        final String UPDATE_OLD_HERO = "UPDATE hero SET superPower_id = ? "
                + "WHERE id = ?";

        jdbc.update(UPDATE_OLD_HERO,
                newId,
                oldHeroId);

        System.out.println("hero updateSuperPowerHero " + hero.toString());
        final String UPDATE_SUPER_POWER_HERO = "UPDATE hero SET superPower_id = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_SUPER_POWER_HERO,
                hero.getSuperPower_id(),
                hero.getId());
    }

    @Override
    public void deleteSuperPowerHero(Hero hero) {

        final String DELETE_SUPER_POWER_HERO = "DELETE superPower_id FROM hero WHERE id = ?";
        jdbc.update(DELETE_SUPER_POWER_HERO, hero.getId());

    }

    public static final class SuperPowerMapper implements RowMapper<SuperPower> {

        @Override
        public SuperPower mapRow(ResultSet rs, int index) throws SQLException {

            SuperPower superHero = new SuperPower();
            superHero.setId(rs.getInt("id"));
            superHero.setName(rs.getString("name"));

            return superHero;
        }
    }

}
