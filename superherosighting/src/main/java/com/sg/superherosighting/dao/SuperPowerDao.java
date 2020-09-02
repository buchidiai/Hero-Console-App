/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.dao;

import com.sg.superherosighting.entities.SuperPower;
import java.util.List;

/**
 *
 * @author louie
 */
public interface SuperPowerDao {

    SuperPower getSuperPowerById(int id);

    void insertSuperPowerHero(SuperPower superPower);

    List<SuperPower> getAllSuperPowers();

    SuperPower addSuperPower(SuperPower superPower);

    void updateSuperPower(SuperPower superPower);

    void deleteSuperPowerById(int id);

}
