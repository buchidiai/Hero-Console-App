/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.entities;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 *
 * @author louie
 */
public class HeroLocation {

    private int id;
    private int heroId;
    private int locationId;
    private LocalDateTime localDate;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getHeroId() {
        return heroId;
    }

    public void setHeroId(int heroId) {
        this.heroId = heroId;
    }

    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }

    public int getLocationId() {
        return locationId;
    }

    public void setLocationId(int locationId) {
        this.locationId = locationId;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + this.id;
        hash = 29 * hash + this.heroId;
        hash = 29 * hash + this.locationId;
        hash = 29 * hash + Objects.hashCode(this.localDate);
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
        final HeroLocation other = (HeroLocation) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.heroId != other.heroId) {
            return false;
        }
        if (this.locationId != other.locationId) {
            return false;
        }
        if (!Objects.equals(this.localDate, other.localDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "HeroLocation{" + "id=" + id + ", heroId=" + heroId + ", locationId=" + locationId + ", localDate=" + localDate + '}';
    }

}
