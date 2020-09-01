/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Sighting;
import java.util.List;

/**
 *
 * @author louie
 */
public interface SightingDao {

    Sighting getSightingById(int heroId, int locationId);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting, Integer existingHeroId, Integer existingLocationId);

    void deleteSightingById(int heroId, int locationId);

}
