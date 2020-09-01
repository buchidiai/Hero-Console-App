/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.entities;

import java.time.LocalDateTime;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 *
 * @author louie
 */
public class Sighting {

    @NotNull
    @NotBlank(message = "Hero must not be empty.")
    private int heroId;
    @NotNull
    @NotBlank(message = "Location must not be empty.")
    private int locationId;
    @NotBlank(message = "Date must not be empty.")
    @Past(message = "Date must be today or in the past.")
    private LocalDateTime localDate;

    private String timeAgo;

    private Hero hero;
    private Location location;

    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }

    public String getTimeAgo() {
        return timeAgo;
    }

    public void setTimeAgo(String timeAgo) {
        this.timeAgo = timeAgo;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    @Override
    public String toString() {
        return "Sighting{" + "heroId=" + heroId + ", locationId=" + locationId + ", localDate=" + localDate + ", hero=" + hero + ", location=" + location + '}';
    }

}
