/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.Sighting;
import com.sg.superherosighting.entities.SuperPower;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author louie
 */
public interface ServiceLayer {

    Set<ConstraintViolation<SuperPower>> validateSuperPower(SuperPower superPower);

    Set<ConstraintViolation<SuperPower>> getSuperPowerViolations();

    Set<ConstraintViolation<Hero>> validateHero(Hero hero);

    Set<ConstraintViolation<Hero>> getHeroViolations();

    Set<ConstraintViolation<Location>> validateLocation(Location location);

    Set<ConstraintViolation<Location>> getLocationViolations();

    Set<ConstraintViolation<Sighting>> validateSighting(Sighting sighting);

    Set<ConstraintViolation<Sighting>> getSightingViolations();

    Set<ConstraintViolation<Organization>> validateOrganization(Organization organization);

    Set<ConstraintViolation<Organization>> getOrganizationViolations();

    //Hero
    Hero getHeroById(int id);

    List<Hero> getAllHeros();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHeroById(int id);

    void insertHeroOrganization(Hero hero);

    void updateHeroOrganization(Hero hero, Organization organization, int originalId);

    void deleteHeroOrganization(Hero hero, Organization organization);

    Hero getHeroDetails(int id);

    //Location
    Location getLocationById(int id);

    void insertLocationHero(Location location);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    void deleteHeroLocation(Hero hero, Location location);

    void updateHeroLocation(Hero hero, Location location, int originalId);

    //Organization
    Organization getOrganizationById(int id);

    void insertOrganizationHero(Organization organization);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);

    //superPower
    SuperPower getSuperPowerById(int id);

    void insertSuperPowerHero(SuperPower superPower);

    List<SuperPower> getAllSuperPowers();

    SuperPower addSuperPower(SuperPower superPower);

    void updateSuperPower(SuperPower superPower);

    Hero getSuperPowerDetails(int superPowerId);

    void updateSuperPowerHero(Hero hero, int oldHeroId);

    void deleteSuperPowerHero(Hero hero);

    //sighting
    void deleteSuperPowerById(int id);

    Sighting getSightingById(int heroId, int locationId);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting, Integer existingHeroId, Integer existingLocationId);

    void deleteSightingById(int heroId, int locationId);

}
