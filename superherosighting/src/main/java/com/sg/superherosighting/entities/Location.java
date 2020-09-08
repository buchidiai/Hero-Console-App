/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.sg.superherosighting.entities;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

/**
 *
 * @author louie
 */
public class Location {

    private int id;
    @NotBlank(message = "Name must not be empty.")
    @Size(max = 45, message = "Name must be less than 45 characters.")
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotBlank(message = "Description must not be empty.")
    @Size(max = 255, message = "Description must be less than 255 characters.")
    @NotNull(message = "Description cannot be null")
    private String description;
    @NotBlank(message = "Address must not be empty.")
    private String address;
    @NotBlank(message = "Latitude must not be empty.")
    @Size(max = 15, message = "latitude must be less than 15 characters.")
    private String latitude;
    @NotBlank(message = "Latitude must not be empty.")
    @Size(max = 15, message = "Latitude must be less than 15 characters.")
    private String longitude;

    private List<Hero> heros;

//    @Past(message = "Date must be today or in the past.")
    private LocalDateTime localDate;

    private int sightingId;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getLatitude() {
        return latitude;
    }

    public void setLatitude(String latitude) {
        this.latitude = latitude;
    }

    public String getLongitude() {
        return longitude;
    }

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public List<Hero> getHeros() {
        return heros;
    }

    public void setHeros(List<Hero> heros) {
        this.heros = heros;
    }

    public LocalDateTime getLocalDate() {
        return localDate;
    }

    public void setLocalDate(LocalDateTime localDate) {
        this.localDate = localDate;
    }

    public int getSightingId() {
        return sightingId;
    }

    public void setSightingId(int sightingId) {
        this.sightingId = sightingId;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 67 * hash + this.id;
        hash = 67 * hash + Objects.hashCode(this.name);
        hash = 67 * hash + Objects.hashCode(this.description);
        hash = 67 * hash + Objects.hashCode(this.address);
        hash = 67 * hash + Objects.hashCode(this.latitude);
        hash = 67 * hash + Objects.hashCode(this.longitude);
        hash = 67 * hash + Objects.hashCode(this.heros);
        hash = 67 * hash + Objects.hashCode(this.localDate);
        hash = 67 * hash + this.sightingId;
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
        final Location other = (Location) obj;
        if (this.id != other.id) {
            return false;
        }
        if (this.sightingId != other.sightingId) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        if (!Objects.equals(this.heros, other.heros)) {
            return false;
        }
        if (!Objects.equals(this.localDate, other.localDate)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Location{" + "id=" + id + ", name=" + name + ", description=" + description + ", address=" + address + ", latitude=" + latitude + ", longitude=" + longitude + ", heros=" + heros + ", localDate=" + localDate + ", sightingId=" + sightingId + '}';
    }

}
