/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import java.util.List;

/**
 *
 * @author louie
 */
public interface LocationDao {

    Location getLocationById(int id);

    void updateLocationHero(Hero hero, Location location, int originalId);

    Location getLocationDetails(int id);

    List<Location> getAllLocations();

    Location addLocation(Location location);

    void updateLocation(Location location);

    void deleteLocationById(int id);

}
