/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.dao.OrganizationDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.Organization;
import com.sg.superherosighting.entities.SuperPower;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author louie
 */
@Service
public class ServiceLayerImpl implements ServiceLayer {

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    HeroDao heroDao;

    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao organizationDao;

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

}
