/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.dao.HeroDao;
import com.sg.superherosighting.dao.SightingDao;
import com.sg.superherosighting.dao.SuperPowerDao;
import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.Location;
import com.sg.superherosighting.entities.SuperPower;
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
public class HeroSeviceLayerImpl implements HeroSeviceLayer {

    @Autowired
    private SuperPowerDao superPowerDao;

    @Autowired
    private HeroDao heroDao;

    @Autowired
    private SightingDao sightingDao;

    private Set<ConstraintViolation<Hero>> heroViolations = new HashSet<>();

    private Validator validate = Validation.buildDefaultValidatorFactory().getValidator();

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

        SuperPower superPower = new SuperPower();

        superPower.setName("none");

        SuperPower defaultSuperPower = getDefaultSuperPower(superPower);

        hero.setSuperPower_id(defaultSuperPower.getId());
        hero.setSuperPower(defaultSuperPower.getName());

        return heroDao.addHero(hero);

    }

    @Override
    public void updateHero(Hero hero) {

        //check if user didnt select a location and get exisitng locations if any and delete them
        if (hero.getLocations() == null) {

            hero.setLocations(heroDao.getHeroDetails(hero.getId()).getLocations());

            for (Location location : hero.getLocations()) {

                sightingDao.deleteSightingById(location.getSightingId());

            }
            //set locations to null after deleteing them
            hero.setLocations(null);
        }

        //update hero object
        heroDao.updateHero(hero);
    }

    @Override
    public void deleteHeroById(int id) {
        heroDao.deleteHeroById(id);
    }

    @Override
    public Hero getHeroDetails(int id) {
        return heroDao.getHeroDetails(id);
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

    @Override
    public void deleteHeroLocation(Hero hero, Location location) {
        heroDao.deleteHeroLocation(hero, location);
    }

    @Override
    public Set<ConstraintViolation<Hero>> validateHero(Hero hero) {
        return heroViolations = validate.validate(hero);
    }

    @Override
    public Set<ConstraintViolation<Hero>> getHeroViolations() {
        return heroViolations;
    }

}
