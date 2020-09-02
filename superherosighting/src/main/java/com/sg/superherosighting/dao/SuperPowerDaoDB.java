/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.SuperPower;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
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
    public SuperPower addSuperPower(SuperPower superPower) {
        final String INSERT_SUPERPOWER = "INSERT INTO superPower(name)"
                + "VALUES(?)";

        jdbc.update(INSERT_SUPERPOWER,
                superPower.getName());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

        superPower.setId(newId);

        return superPower;
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {
        final String UPDATE_SUPERPOWER = "UPDATE superPower SET name = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_SUPERPOWER,
                superPower.getName(),
                superPower.getId());
    }

    @Override
    public void deleteSuperPowerById(int id) {

        final String DELETE_SUPERPOWER = "DELETE FROM superPower WHERE id = ?";
        jdbc.update(DELETE_SUPERPOWER, id);
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
