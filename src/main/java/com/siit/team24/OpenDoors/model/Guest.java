package com.siit.team24.OpenDoors.model;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;

import java.util.Set;


@Entity
public class Guest extends User{
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<Accommodation> favorites;
    public Guest(){

    }

    public Set<Accommodation> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Accommodation> favorites) {
        this.favorites = favorites;
    }
}
