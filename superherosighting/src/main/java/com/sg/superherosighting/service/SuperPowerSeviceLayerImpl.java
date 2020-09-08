/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

/**
 *
 * @author louie
 */
@Service
public class SuperPowerSeviceLayerImpl implements SuperPowerSeviceLayer {

    @Autowired
    private SuperPowerDao superPowerDao;

    private Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

    private Set<ConstraintViolation<SuperPower>> superPowerViolations = new HashSet<>();

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

        return getDefaultSuperPower(superPower);

    }

    @Override
    public void updateSuperPower(SuperPower superPower, BindingResult result) {

        List<SuperPower> superPowers = superPowerDao.getSuperPowerByName(superPower.getName());

        //check for existing super power with that name
        if (superPowers.isEmpty()) {

            superPowerDao.updateSuperPower(superPower);
        } else {

            //check if old name and new name is the same then send error
            if (superPower.getName() == superPowers.get(0).getName()) {
                FieldError error = new FieldError("superpower", "name", superPower.getName() + " is already taken.");
                result.addError(error);
            } else {
                //names are diffrent update super power
                superPowerDao.updateSuperPower(superPower);
            }
        }
    }

    @Override
    public void deleteSuperPowerById(int id) {

        SuperPower superPowerToDelete = superPowerDao.getSuperPowerById(id);

        // create new super power
        SuperPower superPower = new SuperPower();

        superPower.setName("none");

        //check if it super power called none exists
        List<SuperPower> superPowers = superPowerDao.getSuperPowerByName(superPower.getName());

        //default value doesnt exist in the db
        if (superPowers.isEmpty()) {

            //create one
            superPower = superPowerDao.addSuperPower(superPower);

            //get all heros
            List<Hero> heros = superPowerDao.getSuperPowerDetails(id);

            //add to super Power object
            superPower.setHeros(heros);

            //update all heros with id so they can be deleted
            superPowerDao.updateSuperPower(superPower);

            //delete id that was meant to be deleted
            superPowerDao.deleteSuperPowerById(id);

        } else {

            //if it does exist
            //check if power to be deleted == none , delete immediately
            if (superPowerToDelete.getName().equals("none")) {

            } else {
                //power that will be deleted is not "none"

                //get exusting one
                superPower = superPowers.get(0);

                //get all heros
                List<Hero> heros = superPowerDao.getSuperPowerDetails(id);

                //add to super Power object
                superPower.setHeros(heros);

                //update all heros with id so they can be deleted
                superPowerDao.updateSuperPower(superPower);

                //delete id that was meant to be deleted
                superPowerDao.deleteSuperPowerById(id);
            }

        }

    }

    @Override
    public Set<ConstraintViolation<SuperPower>> validateSuperPower(SuperPower superPower) {

        return superPowerViolations = validate.validate(superPower);
    }

    @Override
    public Set<ConstraintViolation<SuperPower>> getSuperPowerViolations() {
        return superPowerViolations;
    }

    @Override
    public List<Hero> getSuperPowerDetails(int superPowerId) {
        return superPowerDao.getSuperPowerDetails(superPowerId);
    }

    private SuperPower getDefaultSuperPower(SuperPower superPower) {

        List<SuperPower> superPowers = superPowerDao.getSuperPowerByName(superPower.getName());

        if (superPowers.isEmpty()) {
            //create new super power if not found
            superPower = superPowerDao.addSuperPower(superPower);

        } else {
            //return existing
            superPower = superPowers.get(0);

        }

        return superPower;

    }
}
