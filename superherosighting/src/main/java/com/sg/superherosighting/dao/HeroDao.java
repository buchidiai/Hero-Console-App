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
public interface HeroDao {

    Hero getHeroById(int id);

    Hero getHeroDetails(int id);

    List<Hero> getAllHeros();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHeroLocation(Hero hero, Location location);

    void deleteHeroById(int id);

}
