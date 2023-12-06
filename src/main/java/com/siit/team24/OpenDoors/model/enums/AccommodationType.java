package com.siit.team24.OpenDoors.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum AccommodationType {
    ENTIRE_HOME("Entire Home"),
    APARTMENT("Apartment"),
    HOLIDAY_HOME("Holiday Home"),
    HOTEL("Hotel"),
    VILLA("Villa"),
    COTTAGE("Cottage"),
    STUDIO_APARTMENT("Studio Apartment"),
    ROOM("Room");

    private final String value;
    private static final Map<String, AccommodationType> enumMap = new HashMap<>();

    static {
        for (AccommodationType type : AccommodationType.values()) {
            enumMap.put(type.getValue(), type);
        }
    }

    AccommodationType(String stringValue) {
        this.value = stringValue;
    }

    public String getValue() {
        return value;
    }

    public static AccommodationType fromString(String stringValue) {
        return enumMap.get(stringValue);
    }
}

