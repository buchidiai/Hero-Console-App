/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.service;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
import java.util.List;
import java.util.Set;
import javax.validation.ConstraintViolation;
import org.springframework.validation.BindingResult;

/**
 *
 * @author louie
 */
public interface SuperPowerSeviceLayer {

    SuperPower getSuperPowerById(int id);

    List<SuperPower> getAllSuperPowers();

    SuperPower addSuperPower(SuperPower superPower);

    void deleteSuperPowerById(int id);

    void updateSuperPower(SuperPower superPower, BindingResult result);

    List<Hero> getSuperPowerDetails(int superPowerId);

    Set<ConstraintViolation<SuperPower>> validateSuperPower(SuperPower superPower);

    Set<ConstraintViolation<SuperPower>> getSuperPowerViolations();

}
