/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.HeroDaoDB.HeroLocationMapper;
import com.sg.superherosighting.dao.HeroDaoDB.HeroMapper;
import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.HeroLocation;
import com.sg.superherosighting.entities.Location;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
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
//test
@Repository
public class LocationDaoDB implements LocationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Location getLocationById(int id) {
        try {
            final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";
            Location location = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), id);

            return location;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public List<Location> getAllLocations() {

        try {

            final String SELECT_ALL_LOCATIONS = "SELECT * FROM location";
            List<Location> locations = jdbc.query(SELECT_ALL_LOCATIONS, new LocationMapper());

            return locations;
        } catch (DataAccessException ex) {

            return null;
        }

    }

    @Override
    @Transactional
    public Location addLocation(Location location) {

        final String INSERT_LOCATION = "INSERT INTO location (name,description,address,latitude,longitude) "
                + "VALUES(?,?,?,?,?)";
        jdbc.update(INSERT_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        location.setId(newId);
        return location;
    }

    @Override
    @Transactional
    public void updateLocation(Location location) {
        final String UPDATE_LOCATION = "UPDATE location SET name = ?, description = ?, address = ?,latitude = ?,longitude = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_LOCATION,
                location.getName(),
                location.getDescription(),
                location.getAddress(),
                location.getLatitude(),
                location.getLongitude(),
                location.getId()
        );
    }

    @Override
    @Transactional
    public void deleteLocationById(int id) {

        final String DELETE_LOCATION_HERO = "DELETE FROM sighting WHERE location_id = ?";

        jdbc.update(DELETE_LOCATION_HERO, id);

        final String DELETE_LOCATION = "DELETE FROM location WHERE id = ?";
        jdbc.update(DELETE_LOCATION, id);
    }

    @Override
    @Transactional
    public Location getLocationDetails(int id) {

        final String SELECT_LOCATION = "SELECT * FROM  location  WHERE id = ?";

        Location location = jdbc.queryForObject(SELECT_LOCATION, new LocationMapper(), id);

        getLocationHeros(location);
        return location;

    }

    private void getLocationHeros(Location location) {

        final String SELECT_LOCATION_DATE = "SELECT * FROM  sighting WHERE location_id = ?";

        List<HeroLocation> locationHeros = jdbc.query(SELECT_LOCATION_DATE, new HeroLocationMapper(), location.getId());

        final String SELECT_HERO_BY_ID = "SELECT h.id, h.name, h.description, s.name FROM  hero h JOIN superPower s On h.superPower_id = s.id WHERE h.id = ?";

        List<Hero> heros = new ArrayList<>();
        for (HeroLocation heroLocation : locationHeros) {

            Hero foundHero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), heroLocation.getHeroId());

            location.setLocalDate(heroLocation.getLocalDate());
            heros.add(foundHero);
        }
        location.setHeros(heros);
    }

    @Override
    public void updateLocationHero(Hero hero, Location location, int originalId) {

        final String UPDATE_HERO_LOCATION = "UPDATE sighting SET hero_id = ?,  location_id = ?   WHERE hero_id = ?  AND  location_id = ?";

        jdbc.update(UPDATE_HERO_LOCATION, hero.getId(), location.getId(), originalId, location.getId());

    }

    public static final class LocationMapper implements RowMapper<Location> {

        @Override
        public Location mapRow(ResultSet rs, int index) throws SQLException {
            Location location = new Location();
            location.setId(rs.getInt("id"));
            location.setName(rs.getString("name"));
            location.setDescription(rs.getString("description"));
            location.setAddress(rs.getString("address"));
            location.setLatitude(rs.getString("latitude"));
            location.setLongitude(rs.getString("longitude"));
            return location;
        }
    }

}
