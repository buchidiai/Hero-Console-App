/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
import java.util.List;

/**
 *
 * @author louie
 */
public interface SuperPowerDao {

    SuperPower getSuperPowerById(int id);

    List<SuperPower> getSuperPowerByName(String name);

    List<Hero> getSuperPowerDetails(int superPowerId);

    List<SuperPower> getAllSuperPowers();

    SuperPower addSuperPower(SuperPower superPower);

    void updateSuperPower(SuperPower superPower);

    void deleteSuperPowerById(int id);

}
