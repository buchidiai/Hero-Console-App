/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.controller;

import com.sg.superherosighting.entities.Hero;
import com.sg.superherosighting.entities.SuperPower;
import com.sg.superherosighting.service.HeroSeviceLayer;
import com.sg.superherosighting.service.SuperPowerSeviceLayer;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author louie
 */
@Controller
public class SuperPowerController {

    @Autowired
    private SuperPowerSeviceLayer superPowerService;

    @Autowired
    private HeroSeviceLayer heroService;

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("superPower")
    public String getSuperPowerPage(Model model) {

        model.addAttribute("errors", superPowerService.getSuperPowerViolations());

        return "/superPower/superPower";
    }

    /**
     *
     * @param model
     * @return
     */
    @GetMapping("allSuperPowers")
    public String getAllSuperPowers(Model model) {

        List<SuperPower> superPowers = superPowerService.getAllSuperPowers();

        model.addAttribute("superPowers", superPowers);

        return "/superPower/listSuperPowers";
    }

    /**
     *
     * @param superPower
     * @param result
     * @return
     */
    @PostMapping("addSuperPower")
    public String addSuperPower(@Valid SuperPower superPower, BindingResult result) {

        if (result.hasErrors()) {
            superPowerService.validateSuperPower(superPower);
            return "redirect:superPower";
        }

        superPower = superPowerService.addSuperPower(superPower);

        return "redirect:allSuperPowers";
    }

    /**
     *
     * @param superPowerId
     * @param model
     * @return
     */
    @GetMapping("editSuperPower")
    public String editSuperPower(Integer superPowerId, Model model) {

        SuperPower superPower = superPowerService.getSuperPowerById(superPowerId);

        List<Hero> heros = heroService.getAllHeros();
        model.addAttribute("heros", heros);
        model.addAttribute("superPower", superPower);
        model.addAttribute("errors", superPowerService.getSuperPowerViolations());

        return "/superPower/editSuperPower";
    }

    /**
     *
     * @param superPower
     * @param result
     * @param redirectAttributes
     * @param request
     * @param model
     * @return
     */
    @PostMapping("editSuperPower")
    public String performEditSuperPower(@Valid SuperPower superPower, BindingResult result, RedirectAttributes redirectAttributes,
            HttpServletRequest request, Model model) {

        if (result.hasErrors()) {

            superPowerService.validateSuperPower(superPower);
            redirectAttributes.addAttribute("superPowerId", superPower.getId());

            return "redirect:editSuperPower";
        }

        //heros ids
        String[] heroIds = request.getParameterValues("heroId");

        getAndSetHeros(heroIds, superPower);

        superPowerService.updateSuperPower(superPower, result);

//        if (result.hasErrors()) {
//
//            superPowerService.validateSuperPower(superPower);
//
//            model.addAttribute("errors", superPowerService.getSuperPowerViolations());
//
//            redirectAttributes.addAttribute("superPowerId", superPower.getId());
//
//            return "redirect:editSuperPower";
//        }
        redirectAttributes.addAttribute("superPowerId", superPower.getId());

        return "redirect:superPowerDetails";
    }

    /**
     *
     * @param superPowerId
     * @param heroId
     * @param model
     * @return
     */
    @GetMapping("deleteSuperPowerConfirm")
    public String deleteSuperPowerConfirm(Integer superPowerId, Integer heroId, Model model) {

        model.addAttribute("superPowerId", superPowerId);
        model.addAttribute("heroId", heroId);

        return "/superPower/deleteSuperPowerConfirm";
    }

    /**
     *
     * @param superPowerId
     * @param heroId
     * @return
     */
    @GetMapping("deleteSuperPower")
    public String deleteSuperPower(Integer superPowerId, Integer heroId) {

        superPowerService.deleteSuperPowerById(superPowerId);

        return "redirect:allSuperPowers";
    }

    /**
     *
     * @param superPowerId
     * @param model
     * @return
     */
    @GetMapping("superPowerDetails")
    public String superPowerDetails(Integer superPowerId, Model model) {

        SuperPower superPower = superPowerService.getSuperPowerById(superPowerId);

        List<Hero> heros = superPowerService.getSuperPowerDetails(superPowerId);

        model.addAttribute("heros", heros);
        model.addAttribute("superPower", superPower);

        return "superPower/superPowerDetails";
    }

    private void getAndSetHeros(String[] heroIds, SuperPower superPower) {

        List<Hero> heros = new ArrayList<>();

        if (heroIds != null) {

            //get orgs
            for (String heroId : heroIds) {

                heros.add(heroService.getHeroById(Integer.parseInt(heroId)));
            }
            //set orgs
            superPower.setHeros(heros);
        }

    }
}
