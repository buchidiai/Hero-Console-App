/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author louie
 */
public interface LocationSeviceLayer {

    Location getLocationById(int id);

    List<Location> getAllLocations();

    Location getLocationDetails(int id);

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

    void updateLocationHero(Hero hero, Location location, int originalId);

    Set<ConstraintViolation<Location>> validateLocation(Location location);

    Set<ConstraintViolation<Location>> getLocationViolations();

}
