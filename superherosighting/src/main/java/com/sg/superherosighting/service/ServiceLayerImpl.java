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
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

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
    private Set<ConstraintViolation<Sighting>> sightingViolations = new HashSet<>();
    private Set<ConstraintViolation<Organization>> organizationViolations = new HashSet<>();

    //HERO
    @Override
    public Hero getHeroById(int id) {

        return heroDao.getHeroById(id);
    }

    @Override
    public List<Hero> getAllHeros() {

        List<Hero> heros = heroDao.getAllHeros();

        return heros;
    }

    @Override
    public Hero addHero(Hero hero) {

        SuperPower superPower = new SuperPower();

        superPower.setName("none");

        SuperPower defaultSuperPower = getDefaultSuperPower(superPower);

        hero.setSuperPower_id(defaultSuperPower.getId());
        hero.setSuperPower(defaultSuperPower.getName());

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
    public void insertHeroOrganization(Hero hero) {

        heroDao.insertHeroOrganization(hero);

    }

    @Override
    public Hero getHeroDetails(int id) {
        return heroDao.getHeroDetails(id);
    }

    @Override
    public SuperPower getSuperPowerById(int id) {
        return superPowerDao.getSuperPowerById(id);
    }

    //Super Power
    @Override
    public void insertSuperPowerHero(SuperPower superPower) {

        superPowerDao.insertSuperPowerHero(superPower);
    }

    @Override
    public List<SuperPower> getAllSuperPowers() {
        return superPowerDao.getAllSuperPowers();
    }

    @Override
    public SuperPower addSuperPower(SuperPower superPower) {

        return getDefaultSuperPower(superPower);

    }

    @Override
    public void updateSuperPower(SuperPower superPower, BindingResult result) {

        List<SuperPower> superPowers = superPowerDao.getSuperPowerByName(superPower.getName());

        //check for existing super power with that name
        if (superPowers.isEmpty()) {

            superPowerDao.updateSuperPower(superPower);
        } else {

            //check if old name and new name is the same then send error
            if (superPower.getName() == superPowers.get(0).getName()) {
                FieldError error = new FieldError("superPower", "name", superPower.getName() + " is already taken.");
                result.addError(error);
            } else {
                //names are diffrent update super power
                superPowerDao.updateSuperPower(superPower);
            }
        }
    }

    @Override
    public void deleteSuperPowerById(int id) {

        SuperPower superPowerToDelete = superPowerDao.getSuperPowerById(id);

        // create new super power
        SuperPower superPower = new SuperPower();

        superPower.setName("none");

        //check if it super power called none exists
        List<SuperPower> superPowers = superPowerDao.getSuperPowerByName(superPower.getName());

        //default value doesnt exist in the db
        if (superPowers.isEmpty()) {

            //create one
            superPower = superPowerDao.addSuperPower(superPower);

            //get all heros
            List<Hero> heros = superPowerDao.getSuperPowerDetails(id);

            //add to super Power object
            superPower.setHeros(heros);

            //update all heros with id so they can be deleted
            superPowerDao.updateSuperPower(superPower);

            //delete id that was meant to be deleted
            superPowerDao.deleteSuperPowerById(id);

        } else {

            //if it does exist
            //check if power to be deleted == none , delete immediately
            if (superPowerToDelete.getName().equals("none")) {

            } else {
                //power that will be deleted is not "none"

                //get exusting one
                superPower = superPowers.get(0);

                //get all heros
                List<Hero> heros = superPowerDao.getSuperPowerDetails(id);

                //add to super Power object
                superPower.setHeros(heros);

                //update all heros with id so they can be deleted
                superPowerDao.updateSuperPower(superPower);

                //delete id that was meant to be deleted
                superPowerDao.deleteSuperPowerById(id);
            }

        }

    }

    @Override
    public Location getLocationById(int id) {
        return locationDao.getLocationById(id);
    }

    @Override
    public void insertLocationHero(Location location) {
        locationDao.insertLocationHero(location);
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
    public void insertOrganizationHero(Organization organization) {
        organizationDao.insertOrganizationHero(organization);
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

        return sightingDao.getSightingById(id);
    }

    @Override
    public void deleteSightingById(int heroId, int locationId, int sightingId) {
        sightingDao.deleteSightingById(heroId, locationId, sightingId);
    }

    @Override
    public void updateSighting(Sighting sighting, Integer existingHeroId, Integer existingLocationId) {

        sightingDao.updateSighting(sighting, existingHeroId, existingLocationId);
    }

    @Override
    public void updateHeroOrganization(Hero hero, Organization organization, int originalId) {
        heroDao.updateHeroOrganization(hero, organization, originalId);
    }

    @Override
    public void deleteHeroOrganization(Hero hero, Organization organization) {
        heroDao.deleteHeroOrganization(hero, organization);
    }

    @Override
    public void deleteHeroLocation(Hero hero, Location location) {
        heroDao.deleteHeroLocation(hero, location);
    }

    @Override
    public void updateHeroLocation(Hero hero, Location location, int originalId) {
        heroDao.updateHeroLocation(hero, location, originalId);
    }

    @Override
    public List<Hero> getSuperPowerDetails(int superPowerId) {
        return superPowerDao.getSuperPowerDetails(superPowerId);
    }

    @Override
    public void updateSuperPowerHero(Hero hero, int oldHeroId) {
        superPowerDao.updateSuperPowerHero(hero, oldHeroId);
    }

    @Override
    public void deleteSuperPowerHero(Hero hero) {
        superPowerDao.deleteSuperPowerHero(hero);
    }

    @Override
    public Location getLocationDetails(int id) {
        return locationDao.getLocationDetails(id);
    }

    @Override
    public void updateLocationHero(Hero hero, Location location, int originalId) {
        locationDao.updateLocationHero(hero, location, originalId);
    }

    @Override
    public Organization getOrganizationDetails(int id) {
        return organizationDao.getOrganizationDetails(id);
    }

    @Override
    public void updateOrganizationHero(Hero hero, Organization organization, int originalId) {

        organizationDao.updateOrganizationHero(hero, organization, originalId);
    }

    @Override
    public Set<ConstraintViolation<SuperPower>> validateSuperPower(SuperPower superPower) {

        return superPowerViolations = validate.validate(superPower);
    }

    @Override
    public Set<ConstraintViolation<SuperPower>> getSuperPowerViolations() {
        return superPowerViolations;
    }

    @Override
    public Set<ConstraintViolation<Hero>> validateHero(Hero hero) {
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

    @Override
    public Set<ConstraintViolation<Sighting>> validateSighting(Sighting sighting) {

        return sightingViolations = validate.validate(sighting);
    }

    @Override
    public Set<ConstraintViolation<Sighting>> getSightingViolations() {
        return sightingViolations;
    }

    @Override
    public Set<ConstraintViolation<Organization>> validateOrganization(Organization organization) {
        return organizationViolations = validate.validate(organization);
    }

    @Override
    public Set<ConstraintViolation<Organization>> getOrganizationViolations() {
        return organizationViolations;
    }

    private SuperPower getDefaultSuperPower(SuperPower superPower) {

        List<SuperPower> superPowers = superPowerDao.getSuperPowerByName(superPower.getName());

        if (superPowers.isEmpty()) {
            //create new super power if not found
            superPower = superPowerDao.addSuperPower(superPower);

        } else {
            //return existing
            superPower = superPowers.get(0);

        }

        return superPower;

    }

}
