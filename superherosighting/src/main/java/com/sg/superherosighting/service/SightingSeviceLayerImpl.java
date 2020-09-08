/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.entities.Sighting;
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
public class SightingSeviceLayerImpl implements SightingSeviceLayer {

    @Autowired
    private SightingDao sightingDao;

    private Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    private Set<ConstraintViolation<Sighting>> sightingViolations = new HashSet<>();

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
        sightingDao.deleteSightingByIds(heroId, locationId, sightingId);
    }

    @Override
    public void updateSighting(Sighting sighting, Integer existingHeroId, Integer existingLocationId) {

        sightingDao.updateSighting(sighting, existingHeroId, existingLocationId);
    }

    @Override
    public void deleteSuperPowerById(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public Set<ConstraintViolation<Sighting>> validateSighting(Sighting sighting) {

        return sightingViolations = validate.validate(sighting);
    }

    @Override
    public Set<ConstraintViolation<Sighting>> getSightingViolations() {
        return sightingViolations;
    }

}
