/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import java.util.List;

/**
 *
 * @author louie
 */
public interface HeroDao {

    Hero getHeroById(int id);

    void insertHeroOrganization(Hero hero);

    List<Hero> getAllHeros();

    Hero addHero(Hero hero);

    void updateHero(Hero hero);

    void deleteHeroById(int id);

}
