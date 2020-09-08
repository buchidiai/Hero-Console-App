/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Past;

/**
 *
 * @author louie
 */
public class Sighting {

    private int id;
    @NotNull
    @Min(value = 1, message = "Please select a Hero")
    private int heroId;
    @NotNull
    @Min(value = 1, message = "Please select a Location")
    private int locationId;
//    @NotNull(message = "Please select a Date")
    @Past(message = "Date must be today or in the past.")
////    @DateTimeFormat(pattern = "MM-dd-yyyy")
//    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDateTime localDate;

    @NotNull(message = "Please select a Date")
    @NotBlank(message = "Date must not be empty.")
    private String Date;

    private String timeAgo;

    private List<Hero> heros;

    private Hero hero;

    private Location location;

    public String getDate() {
        return Date;
    }

    public void setDate(String Date) {
        this.Date = Date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

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

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public List<Hero> getHeros() {
        return heros;
    }

    public void setHeros(List<Hero> heros) {
        this.heros = heros;
    }

    public Hero getHero() {
        return hero;
    }

    public void setHero(Hero hero) {
        this.hero = hero;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 59 * hash + this.id;
        hash = 59 * hash + this.heroId;
        hash = 59 * hash + this.locationId;
        hash = 59 * hash + Objects.hashCode(this.localDate);
        hash = 59 * hash + Objects.hashCode(this.Date);
        hash = 59 * hash + Objects.hashCode(this.timeAgo);
        hash = 59 * hash + Objects.hashCode(this.heros);
        hash = 59 * hash + Objects.hashCode(this.hero);
        hash = 59 * hash + Objects.hashCode(this.location);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Sighting other = (Sighting) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.heroId != other.heroId) {
            return false;
        }
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.Date, other.Date)) {
            return false;
        }
        if (!Objects.equals(this.timeAgo, other.timeAgo)) {
            return false;
        }
        if (!Objects.equals(this.localDate, other.localDate)) {
            return false;
        }
        if (!Objects.equals(this.heros, other.heros)) {
            return false;
        }
        if (!Objects.equals(this.hero, other.hero)) {
            return false;
        }
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Sighting{" + "id=" + id + ", heroId=" + heroId + ", locationId=" + locationId + ", localDate=" + localDate + ", Date=" + Date + ", timeAgo=" + timeAgo + ", heros=" + heros + ", hero=" + hero + ", location=" + location + '}';
    }

}
