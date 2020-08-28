/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Location;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 *
 * @author louie
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class LocationDaoDBTest {

    @Autowired
    LocationDao locationDao;

    @Autowired
    SuperPowerDao superPowerDao;

    @Autowired
    OrganizationDao organizationDao;

    Location location1 = null;

    Location location2 = null;

    public LocationDaoDBTest() {

    }

    @BeforeEach
    public void setUp() {

        List<Location> locations = locationDao.getAllLocations();

        for (Location location : locations) {
            locationDao.deleteLocationById(location.getId());
        }

        //add location
        location1 = new Location();
        location1.setName("Dallas");
        location1.setDescription("Robbery at City Bank");
        location1.setAddress("200 dallas dr, dallas tx 79654");
        location1.setLatitude("31.9686");
        location1.setLongitude("99.9018");

        //add location 2
        location2 = new Location();
        location2.setName("Houston");
        location2.setDescription("Cat stuck on tree");
        location2.setAddress("543 emily park rd, houston tx 79567");
        location2.setLatitude("29.7604");
        location2.setLongitude("95.3698");

    }

    @Test
    public void testAddLocation() {
        //add location
        location1 = locationDao.addLocation(location1);

        //get location
        Location foundLocation = locationDao.getLocationById(location1.getId());

        assertEquals(location1, foundLocation);

    }

    @Test
    public void testGetLocation() {
        //add location
        location1 = locationDao.addLocation(location1);

        //get location
        Location foundLocation = locationDao.getLocationById(location1.getId());

        assertEquals(location1, foundLocation);
    }

    @Test
    public void testGetAllLocations() {
        //add locations
        location1 = locationDao.addLocation(location1);

        location2 = locationDao.addLocation(location2);

        List<Location> locations = locationDao.getAllLocations();

        assertEquals(2, locations.size());

        assertTrue(locations.contains(location1));
        assertTrue(locations.contains(location2));

    }

    @Test
    public void testUpdateLocation() {
        //add location
        location1 = locationDao.addLocation(location1);

        Location foundLocation = locationDao.getLocationById(location1.getId());

        assertEquals(location1, foundLocation);

        location1.setDescription("Robery as federal reserve");

        locationDao.updateLocation(location1);

        Location updatedLocation = locationDao.getLocationById(location1.getId());

        assertNotEquals(foundLocation, updatedLocation);

    }

    @Test
    public void testDeleteLocation() {

        //add location
        location1 = locationDao.addLocation(location1);

        Location foundLocation = locationDao.getLocationById(location1.getId());

        assertEquals(location1, foundLocation);

        locationDao.deleteLocationById(location1.getId());

        Location deletedLocation = locationDao.getLocationById(location1.getId());

        assertNull(deletedLocation);

    }

}
