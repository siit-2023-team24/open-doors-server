package com.siit.team24.OpenDoors.model.enums;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private final String amenityName;
    private static final Map<String, Amenity> amenityMap = new HashMap<>();

    static {
        for (Amenity amenity : Amenity.values()) {
            amenityMap.put(amenity.getAmenityName(), amenity);
        }
    }

    Amenity(String amenityName) {
        this.amenityName = amenityName;
    }

    public String getAmenityName() {
        return amenityName;
    }

    public static Amenity fromString(String amenityName) {
        return amenityMap.get(amenityName);
    }

    public static List<Amenity> fromStringList(List<String> amenityNames) {
        List<Amenity> amenities = new ArrayList<>();
        for (String amenityName : amenityNames) {
            amenities.add(fromString(amenityName));
        }
        return amenities;
    }

    public static List<String> fromAmenityList(List<Amenity> amenities) {
        List<String> amenityNames = new ArrayList<>();
        if (amenities != null) {
            for (Amenity amenity : amenities) {
                amenityNames.add(amenity.getAmenityName());
            }
        }
        return amenityNames;
    }
}