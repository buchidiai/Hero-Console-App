/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.HeroOrganization;
import com.sg.superherosighting.entities.Organization;
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
public class OrganizationDaoDB implements OrganizationDao {

    @Autowired
    JdbcTemplate jdbc;

    @Override
    public Organization getOrganizationById(int id) {
        try {
            final String SELECT_ORGANIZATION_BY_ID = "SELECT * FROM organization WHERE id = ?";
            Organization organization = jdbc.queryForObject(SELECT_ORGANIZATION_BY_ID, new OrganizationMapper(), id);

            return organization;
        } catch (DataAccessException ex) {
            return null;
        }
    }

    @Override
    public void insertOrganizationHero(Organization organization) {
        final String INSERT_ORGANIZATION_HERO = "INSERT INTO hero_has_organization(hero_id, organization_id) VALUES(?,?)";

        for (Hero hero : organization.getHeros()) {

            jdbc.update(INSERT_ORGANIZATION_HERO,
                    hero.getId(),
                    organization.getId());
        }
    }

    @Override
    public List<Organization> getAllOrganizations() {
        final String SELECT_ALL_ORGANIZATIONS = "SELECT * FROM organization";
        return jdbc.query(SELECT_ALL_ORGANIZATIONS, new OrganizationMapper());
    }

    @Override
    public Organization addOrganization(Organization organization) {
        final String INSERT_ORGANIZATION = "INSERT INTO organization(name,description,address) "
                + "VALUES(?,?,?)";
        jdbc.update(INSERT_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress()
        );

        int newId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
        organization.setId(newId);
        return organization;
    }

    @Override
    public void updateOrganization(Organization organization) {
        final String UPDATE_ORGANIZATION = "UPDATE organization SET name = ?, description = ?, address = ? "
                + "WHERE id = ?";
        jdbc.update(UPDATE_ORGANIZATION,
                organization.getName(),
                organization.getDescription(),
                organization.getAddress(),
                organization.getId()
        );
    }

    @Override
    public void deleteOrganizationById(int id) {
        final String DELETE_ORGANIZATION_HERO = "DELETE FROM hero_has_organization WHERE organization_id = ?";
        jdbc.update(DELETE_ORGANIZATION_HERO, id);

        final String DELETE_ORGANIZATION = "DELETE FROM organization WHERE id = ?";
        jdbc.update(DELETE_ORGANIZATION, id);
    }

    @Override
    @Transactional
    public Organization getOrganizationDetails(int id) {

        final String SELECT_LOCATION = "SELECT * FROM  organization  WHERE id = ?";

        Organization organization = jdbc.queryForObject(SELECT_LOCATION, new OrganizationMapper(), id);

        getOrganizationHeros(organization);
        return organization;
    }

    private void getOrganizationHeros(Organization organization) {

        final String SELECT_LOCATION_DATE = "SELECT * FROM  hero_has_organization WHERE organization_id = ?";

        List<HeroOrganization> organizationHeros = jdbc.query(SELECT_LOCATION_DATE, new OrganizationHeroMapper(), organization.getId());

        final String SELECT_HERO_BY_ID = "SELECT h.id, h.name, h.description, s.name FROM  hero h JOIN superPower s On h.superPower_id = s.id WHERE h.id = ? ORDER BY h.name ASC";

        List<Hero> heros = new ArrayList<>();
        for (HeroOrganization organizationHero : organizationHeros) {

            Hero foundHero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroDaoDB.HeroMapper(), organizationHero.getHeroId());
            heros.add(foundHero);
        }

        organization.setHeros(heros);

    }

    @Override
    public void updateOrganizationHero(Hero hero, Organization organization, int originalId) {

        final String UPDATE_ORGANIZATION_HERO = "UPDATE hero_has_organization SET hero_id = ?,  organization_id = ?   WHERE hero_id = ?  AND  organization_id = ?";

        jdbc.update(UPDATE_ORGANIZATION_HERO, hero.getId(), organization.getId(), originalId, organization.getId());
    }

    public static final class OrganizationMapper implements RowMapper<Organization> {

        @Override
        public Organization mapRow(ResultSet rs, int index) throws SQLException {
            Organization organization = new Organization();
            organization.setId(rs.getInt("id"));
            organization.setName(rs.getString("name"));
            organization.setDescription(rs.getString("description"));
            organization.setAddress(rs.getString("address"));
            return organization;
        }
    }

    public static final class OrganizationHeroMapper implements RowMapper<HeroOrganization> {

        @Override
        public HeroOrganization mapRow(ResultSet rs, int index) throws SQLException {
            HeroOrganization organizationHero = new HeroOrganization();
            organizationHero.setHeroId(rs.getInt("hero_id"));
            organizationHero.setOrganizationId(rs.getInt("organization_id"));

            return organizationHero;
        }
    }

}
