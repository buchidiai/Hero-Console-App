/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.SuperPower;
import java.util.List;

/**
 *
 * @author louie
 */
public interface ServiceLayer {

    Hero getHeroById(int id);

    List<Hero> getAllHeros();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHeroById(int id);

    Location getLocationById(int id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    Organization getOrganizationById(int id);

    List<Organization> getAllOrganizations();

    Organization addOrganization(Organization organization);

    void updateOrganization(Organization organization);

    void deleteOrganizationById(int id);

    SuperPower getSuperPowerById(int id);

    List<SuperPower> getAllSuperPowers();

    SuperPower addSuperPower(SuperPower superPower);

    void updateSuperPower(SuperPower superPower);

    void deleteSuperPowerById(int id);

}
