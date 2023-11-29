package com.siit.team24.OpenDoors.model.enums;

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

    AccommodationType(String stringValue) {
        this.value = stringValue;
    }

    public String getValue() {
        return value;
    }
}

