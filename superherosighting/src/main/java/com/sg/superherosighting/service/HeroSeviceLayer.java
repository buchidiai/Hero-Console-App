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
public interface HeroSeviceLayer {

    Hero getHeroById(int id);

    List<Hero> getAllHeros();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHeroById(int id);

    Hero getHeroDetails(int id);

    void deleteHeroLocation(Hero hero, Location location);

    Set<ConstraintViolation<Hero>> validateHero(Hero hero);

    Set<ConstraintViolation<Hero>> getHeroViolations();

}
