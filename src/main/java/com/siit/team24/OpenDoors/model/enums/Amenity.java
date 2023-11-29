package com.siit.team24.OpenDoors.model.enums;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;
public enum Amenity {
    WIFI("Wi-Fi"),
    PARKING("Parking"),
    POOL("Pool"),
    GYM("Gym"),
    AIR_CONDITIONING("Air Conditioning"),
    BREAKFAST_INCLUDED("Breakfast Included"),
    RESTAURANT("Restaurant"),
    PET_FRIENDLY("Pet Friendly"),
    SPA("Spa"),
    TV("TV"),
    KITCHENETTE("Kitchenette"),
    LAUNDRY("Laundry"),
    BALCONY("Balcony"),
    BAR("Bar"),
    SHUTTLE_SERVICE("Shuttle Service"),
    BUSINESS_CENTER("Business Center"),
    CONCIERGE("Concierge"),
    CHILDREN_PLAY_AREA("Children's Play Area"),
    MEETING_ROOMS("Meeting Rooms"),
    FITNESS_CLASSES("Fitness Classes");

    private final String displayName;

    Amenity(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
