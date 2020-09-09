/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Sighting;
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
public class SightingDaoDB implements SightingDao {

    @Autowired
    private JdbcTemplate jdbc;

    @Override
    public Sighting getSightingById(int id) {

        final String SELECT_SIGHTING_BY_IDS = "SELECT * FROM sighting WHERE id = ? ";

        Sighting sighting = jdbc.queryForObject(SELECT_SIGHTING_BY_IDS, new SightingMapper(), id);

        sighting.setHero(getHeroById(sighting.getHeroId()));

        sighting.setLocation(getLocationById(sighting.getLocationId()));

        return sighting;

    }

    private Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            Location location = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationDaoDB.LocationMapper(), id);

            return location;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    private Hero getHeroById(int id) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT h.id, h.name, h.description, s.name FROM  hero h JOIN superPower s On h.superPower_id = s.id WHERE h.id = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroDaoDB.HeroMapper(), id);
            return hero;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Sighting> getAllSightings() {

        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM sighting ORDER by date DESC";

        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());

        getAndSetHerosAndLocation(sightings);

        return sightings;
    }

    private void getAndSetHerosAndLocation(List<Sighting> sightings) {

        for (Sighting sighting : sightings) {
            sighting.setHero(getHeroById(sighting.getHeroId()));
            sighting.setLocation(getLocationById(sighting.getLocationId()));

        }

    }

    @Override
    public Sighting addSighting(Sighting sighting) {

        final String INSERT_INTO_SIGHTING = "INSERT INTO sighting (location_id, hero_id, date) VALUES(?,?,?)";

        for (Hero hero : sighting.getHeros()) {

            jdbc.update(INSERT_INTO_SIGHTING, sighting.getLocationId(), hero.getId(), sighting.getLocalDate());
            int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);

            sighting.setId(newId);
        }

        return sighting;

    }

    @Override
    public void updateSighting(Sighting sighting) {

        final String UPDATE_SIGHTING = "UPDATE sighting SET location_id = ?, hero_id = ?  WHERE id = ?";

        jdbc.update(UPDATE_SIGHTING, sighting.getLocationId(), sighting.getHeroId(), sighting.getId());

    }

    @Override
    public void deleteSightingById(int sightingId) {

        final String DELETE_LOCATION_HERO = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_LOCATION_HERO, sightingId);
    }

    public static final class SightingMapper implements RowMapper<Sighting> {

        @Override
        public Sighting mapRow(ResultSet rs, int index) throws SQLException {

            Sighting sighting = new Sighting();
            sighting.setId(rs.getInt("id"));
            sighting.setHeroId(rs.getInt("hero_id"));
            sighting.setLocationId(rs.getInt("location_id"));
            sighting.setLocalDate(rs.getTimestamp("date").toLocalDateTime());

            return sighting;
        }
    }

}
