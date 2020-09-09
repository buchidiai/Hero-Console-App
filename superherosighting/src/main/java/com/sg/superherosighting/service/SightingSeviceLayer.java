/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.entities.Sighting;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;

/**
 *
 * @author louie
 */
public interface SightingSeviceLayer {

    Sighting getSightingById(int id);

    List<Sighting> getAllSightings();

    Sighting addSighting(Sighting sighting);

    void updateSighting(Sighting sighting, Integer existingHeroId, Integer existingLocationId);

    void deleteSightingById(int sightingId);

    Set<ConstraintViolation<Sighting>> getSightingViolations();

    Set<ConstraintViolation<Sighting>> validateSighting(Sighting sighting);

}
