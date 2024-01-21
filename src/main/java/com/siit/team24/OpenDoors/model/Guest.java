package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.NotificationType;
import com.siit.team24.OpenDoors.model.enums.UserRole;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToMany;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

import java.sql.Timestamp;
import java.util.List;
import java.util.Set;

@SQLDelete(sql = "UPDATE users SET deleted=true WHERE id=?")
//@Where(clause = "deleted=false")
@Entity
public class Guest extends User {
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.REFRESH)
    private Set<Accommodation> favorites;
    public Guest(){

    }

    public Guest(Set<Accommodation> favorites) {
        this.favorites = favorites;
    }

    public Guest(Long id, String email, String password, Timestamp lastPasswordResetDate, UserRole role, String firstName, String lastName, String phone, Image image, Address address, boolean enabled, Set<Accommodation> favorites, List<NotificationType> disabledTypes) {
        super(id, email, password, lastPasswordResetDate, role, firstName, lastName, phone, image, address, enabled, disabledTypes);
        this.favorites = favorites;
    }

    public Set<Accommodation> getFavorites() {
        return favorites;
    }

    public void setFavorites(Set<Accommodation> favorites) {
        this.favorites = favorites;
    }

    public void addFavoriteAccommodation(Accommodation accommodation) {
        this.favorites.add(accommodation);
    }

    public void removeFavoriteAccommodation(Accommodation accommodation) {
        this.favorites.remove(accommodation);
    }
}
