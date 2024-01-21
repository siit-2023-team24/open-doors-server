package com.siit.team24.OpenDoors.model.enums;

import java.util.HashMap;
import java.util.Map;

public enum NotificationType {
    NEW_RESERVATION_REQUEST("New reservation request!"),
    RESERVATION_REQUEST("Reservation request status changed."),
    HOST_REVIEW("You have a new review."),
    ACCOMMODATION_REVIEW("Your accommodation was reviewed.");

    private final String typeMessage;

    private static final Map<String, NotificationType> enumMap = new HashMap<>();

    static {
        for (NotificationType type : NotificationType.values()) {
            enumMap.put(type.getTypeMessage(), type);
        }
    }

    NotificationType(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getTypeMessage() {
        return typeMessage;
    }

    public static NotificationType fromString(String stringValue) {
        return enumMap.get(stringValue);
    }

}
