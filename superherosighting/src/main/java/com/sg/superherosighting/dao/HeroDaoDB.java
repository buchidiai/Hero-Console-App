/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.dao.LocationDaoDB.LocationMapper;
import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.HeroLocation;
import com.sg.superherosighting.entities.HeroOrganization;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.SuperPower;
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
@Repository
public class HeroDaoDB implements HeroDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Hero getHeroById(int id) {
        try {
            final String SELECT_HERO_BY_ID = "SELECT h.id, h.name, h.description, s.name FROM  hero h LEFT JOIN superPower s On h.superPower_id = s.id WHERE h.id = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), id);

            return hero;

        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    @Transactional
    public List<Hero> getAllHeros() {

        try {
            final String SELECT_ALL_HEROS = "SELECT h.id, h.name, h.description, s.name FROM  hero h LEFT JOIN superPower s On h.superPower_id = s.id ORDER by h.name ASC  ";
            List<Hero> heros = jdbc.query(SELECT_ALL_HEROS, new HeroMapper());
            return heros;
        } catch (DataAccessException ex) {

            return null;
        }

    }

    @Override
    @Transactional
    public Hero addHero(Hero hero) {

        final String INSERT_HERO = "INSERT INTO hero (name, description, superPower_id) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperPower_id());

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        hero.setId(newId);
        return hero;
    }

    @Override
    public void insertHeroOrganization(Hero hero) {

        final String INSERT_HERO_ORGANIZATION = "INSERT INTO hero_has_organization(hero_id, organization_id) VALUES(?,?)";

        for (Organization organization : hero.getOrganizations()) {

            jdbc.update(INSERT_HERO_ORGANIZATION,
                    hero.getId(),
                    organization.getId());
        }

    }

    @Override
    @Transactional
    public void updateHero(Hero hero) {

        final String UPDATE_HERO = "UPDATE  hero  SET name = ?, description = ?, superPower_id = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_HERO,
                hero.getName(),
                hero.getDescription(),
                hero.getSuperPower_id(),
                hero.getId()
        );

        updateHeroOrganization(hero);
        updateHeroLocation(hero);

    }

    private void updateHeroLocation(Hero hero) {

        if (hero.getLocations() != null) {
            final String INSERT_HERO_ORGANIZATION = "INSERT INTO sighting (hero_id, location_id, date) VALUES(?,?,?)";

            for (Location location : hero.getLocations()) {
                jdbc.update(INSERT_HERO_ORGANIZATION,
                        hero.getId(),
                        location.getId(),
                        location.getLocalDate());
            }

        }

    }

    private void updateHeroOrganization(Hero hero) {
        //delete old orgs
        final String DELETE_ORGANIZATION_HERO = "DELETE FROM hero_has_organization WHERE hero_id = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, hero.getId());

        if (hero.getOrganizations() != null) {
            //add new orgs
            final String INSERT_HERO_ORGANIZATION = "INSERT INTO hero_has_organization(hero_id, organization_id) VALUES(?,?)";

            for (Organization organization : hero.getOrganizations()) {
                jdbc.update(INSERT_HERO_ORGANIZATION,
                        hero.getId(),
                        organization.getId());
            }
        }

    }

    @Override
    @Transactional
    public void deleteHeroById(int id) {
        final String DELETE_ORGANIZATION_HERO = "DELETE FROM hero_has_organization WHERE hero_id = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, id);

        final String DELETE_LOCATION_HERO = "DELETE FROM sighting WHERE hero_id = ?";
        jdbc.update(DELETE_LOCATION_HERO, id);

        final String DELETE_HERO = "DELETE FROM  hero  WHERE id = ?";
        jdbc.update(DELETE_HERO, id);
    }

    @Override
    @Transactional
    public Hero getHeroDetails(int id) {

        final String SELECT_HERO = "SELECT * FROM  hero h  WHERE h.id = ?";

        Hero hero = jdbc.queryForObject(SELECT_HERO, new HeroMapperOriginal(), id);

        getHeroOrganizations(id, hero);

        getHeroLocations(id, hero);

        if (hero.getSuperPower_id() != -1) {
            getHeroSuperPower(hero);
        }

        return hero;

    }

    private void getHeroSuperPower(Hero hero) {

        final String SELECT_SUPERPOWER_BY_ID = "SELECT * FROM superPower WHERE id = ?";
        SuperPower superPower = jdbc.queryForObject(SELECT_SUPERPOWER_BY_ID, new SuperPowerDaoDB.SuperPowerMapper(), hero.getSuperPower_id());

        hero.setSuperPower(superPower.getName());
        hero.setSuperPower_id(superPower.getId());

    }

    private void getHeroLocations(int id, Hero hero) {

        final String SELECT_ALL_LOCATIONS = "SELECT * FROM sighting WHERE hero_id = ?";

        List<HeroLocation> heroLocations = jdbc.query(SELECT_ALL_LOCATIONS, new HeroLocationMapper(), id);

        final String SELECT_LOCATION_BY_ID = "SELECT * FROM location WHERE id = ?";

        List<Location> locations = new ArrayList<>();

        for (HeroLocation heroLocation : heroLocations) {

            Location location = jdbc.queryForObject(SELECT_LOCATION_BY_ID, new LocationMapper(), heroLocation.getLocationId());
            location.setLocalDate(heroLocation.getLocalDate());
            location.setSightingId(heroLocation.getId());

            locations.add(location);

        }

        hero.setLocations(locations);

    }

    private void getHeroOrganizations(int id, Hero hero) {

        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM hero_has_organization WHERE hero_id = ?";

        List<HeroOrganization> heroOrganizations = jdbc.query(SELECT_ALL_ORGANIZATIONS, new HeroOrganizationMapper(), id);

        final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";

        List<Organization> organizations = new ArrayList<>();
        for (HeroOrganization heroOrganization : heroOrganizations) {

            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationDaoDB.OrganizationMapper(), heroOrganization.getOrganizationId());

            organizations.add(organization);

        }

        hero.setOrganizations(organizations);
    }

    @Override
    public void updateHeroOrganization(Hero hero, Organization organization, int originalId) {

        final String UPDATE_HERO_ORGANIZATION = "UPDATE  hero_has_organization  SET hero_id = ?, organization_id = ?  WHERE hero_id = ? AND organization_id = ? ";

        jdbc.update(UPDATE_HERO_ORGANIZATION, hero.getId(), organization.getId(), hero.getId(), originalId);
    }

    @Override
    public void deleteHeroOrganization(Hero hero, Organization organization) {

        final String DELETE_ORGANIZATION_HERO = "DELETE FROM hero_has_organization WHERE hero_id = ? AND organization_id = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, hero.getId(), organization.getId());

    }

    @Override
    public void deleteHeroLocation(Hero hero, Location location) {

        final String DELETE_ORGANIZATION_HERO = "DELETE FROM sighting WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, location.getSightingId());

    }

    @Override
    public void updateHeroLocation(Hero hero, Location location, int originalId) {

        final String UPDATE_HERO_LOCATION = "UPDATE sighting SET hero_id = ?,  location_id = ?   WHERE hero_id = ?  AND  location_id = ?";

        jdbc.update(UPDATE_HERO_LOCATION, hero.getId(), location.getId(), hero.getId(), originalId);

    }

    public static final class HeroMapper implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {

            Hero hero = new Hero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setSuperPower(rs.getString("s.name") == null ? "null" : rs.getString("s.name"));

            return hero;
        }
    }

    public static final class HeroMapperOriginal implements RowMapper<Hero> {

        @Override
        public Hero mapRow(ResultSet rs, int index) throws SQLException {

            Hero hero = new Hero();
            hero.setId(rs.getInt("id"));
            hero.setName(rs.getString("name"));
            hero.setDescription(rs.getString("description"));
            hero.setSuperPower_id(rs.getString("superPower_id") == null ? -1 : Integer.valueOf(rs.getString("superPower_id")));

            return hero;
        }
    }

    public static final class HeroOrganizationMapper implements RowMapper<HeroOrganization> {

        @Override
        public HeroOrganization mapRow(ResultSet rs, int index) throws SQLException {

            HeroOrganization heroOrganization = new HeroOrganization();
            heroOrganization.setHeroId(rs.getInt("hero_id"));
            heroOrganization.setOrganizationId(rs.getInt("organization_id"));

            return heroOrganization;
        }

    }

    public static final class HeroLocationMapper implements RowMapper<HeroLocation> {

        @Override
        public HeroLocation mapRow(ResultSet rs, int index) throws SQLException {

            HeroLocation heroLocation = new HeroLocation();
            heroLocation.setId(rs.getInt("id"));
            heroLocation.setHeroId(rs.getInt("hero_id"));
            heroLocation.setLocationId(rs.getInt("location_id"));
            heroLocation.setLocalDate(rs.getTimestamp("date") == null ? null : rs.getTimestamp("date").toLocalDateTime());

            return heroLocation;
        }

    }

}
