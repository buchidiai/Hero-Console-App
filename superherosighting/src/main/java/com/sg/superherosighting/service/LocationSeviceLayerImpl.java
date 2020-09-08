/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.LocationDao;
import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
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
public class LocationSeviceLayerImpl implements LocationSeviceLayer {

    @Autowired
    private LocationDao locationDao;

    private Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    private Set<ConstraintViolation<Location>> locationViolations = new HashSet<>();

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
    public Location getLocationDetails(int id) {
        return locationDao.getLocationDetails(id);
    }

    @Override
    public void deleteLocationById(int id) {
        locationDao.deleteLocationById(id);
    }

    @Override
    public void updateLocationHero(Hero hero, Location location, int originalId) {
        locationDao.updateLocationHero(hero, location, originalId);
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
