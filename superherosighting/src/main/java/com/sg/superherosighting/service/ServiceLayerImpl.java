/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.Sighting;
import com.sg.superherosighting.entities.SuperPower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author louie
 */
@Service
public class ServiceLayerImpl implements ServiceLayer {

    @Autowired
    private SuperPowerDao superPowerDao;

    @Autowired
    private HeroDao heroDao;

    @Autowired
    private LocationDao locationDao;

    @Autowired
    private OrganizationDao organizationDao;

    @Autowired
    private SightingDao sightingDao;

    private Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    private Set<ConstraintViolation<SuperPower>> superPowerViolations = new HashSet<>();
    private Set<ConstraintViolation<Hero>> heroViolations = new HashSet<>();
    private Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();

    @Override
    public Hero getHeroById(int id) {

        return heroDao.getHeroById(id);
    }

    @Override
    public List<Hero> getAllHeros() {

        List<Hero> heros = heroDao.getAllHeros();

        heros.forEach(h -> {
            if (h.getSuperPower().isEmpty()) {
                h.setSuperPower("none");

            }
        });
        return heros;
    }

    @Override
    public Hero addHero(Hero hero) {

        return heroDao.addHero(hero);
    }

    @Override
    public void updateHero(Hero hero) {

        heroDao.updateHero(hero);

    }

    @Override
    public void deleteHeroById(int id) {
        heroDao.deleteHeroById(id);
    }

    @Override
    public SuperPower getSuperPowerById(int id) {
        return superPowerDao.getSuperPowerById(id);
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return superPowerDao.getAllSuperPowers();
    }

    @Override
    public SuperPower addSuperPower(SuperPower superPower) {

        return superPowerDao.addSuperPower(superPower);
    }

    @Override
    public void updateSuperPower(SuperPower superPower) {

        superPowerDao.updateSuperPower(superPower);
    }

    @Override
    public void deleteSuperPowerById(int id) {
        superPowerDao.deleteSuperPowerById(id);
    }

    @Override
    public Location getLocationById(int id) {

        return locationDao.getLocationById(id);

    }

    @Override
    public List<Location> getAllLocations() {
        return locationDao.getAllLocations();
    }

    @Override
    public Location addLocation(Location location) {
        return locationDao.addLocation(location);
    }

    @Override
    public void updateLocation(Location location) {
        locationDao.updateLocation(location);
    }

    @Override
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }

    @Override
    public Organization getOrganizationById(int id) {
        return organizationDao.getOrganizationById(id);
    }

    @Override
    public List<Organization> getAllOrganizations() {
        return organizationDao.getAllOrganizations();
    }

    @Override
    public Organization addOrganization(Organization organization) {
        return organizationDao.addOrganization(organization);
    }

    @Override
    public void updateOrganization(Organization organization) {
        organizationDao.updateOrganization(organization);
    }

    @Override
    public void deleteOrganizationById(int id) {
        organizationDao.deleteOrganizationById(id);
    }

    @Override
    public List<Sighting> getAllSightings() {
        return sightingDao.getAllSightings();
    }

    @Override
    public Sighting addSighting(Sighting sighting) {
        return sightingDao.addSighting(sighting);
    }

    @Override
    public Sighting getSightingById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void deleteSightingById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void updateSighting(Sighting sighting) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<ConstraintViolation<SuperPower>> validateSuperPower(SuperPower superPower) {

        //check if valid
        return superPowerViolations = validate.validate(superPower);
    }

    @Override
    public Set<ConstraintViolation<SuperPower>> getSuperPowerViolations() {
        return superPowerViolations;
    }

    @Override
    public Set<ConstraintViolation<Hero>> validateHero(Hero hero) {

        //check if valid
        return heroViolations = validate.validate(hero);
    }

    @Override
    public Set<ConstraintViolation<Hero>> getHeroViolations() {
        return heroViolations;
    }

    @Override
    public Set<ConstraintViolation<Location>> validateLocation(Location location) {

        return locationViolations = validate.validate(location);
    }

    @Override
    public Set<ConstraintViolation<Location>> getLocationViolations() {
        return locationViolations;
    }

}
