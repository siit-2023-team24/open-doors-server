package com.siit.team24.OpenDoors.Domain.Enums;

public enum NotificationType {
    NEW_RESERVATION_REQUEST("New reservation request!"),
    RESERVATION_REQUEST("Reservation request status changed."),
    HOST_REVIEW("You have a new review."),
    ACCOMMODATION_REVIEW("Your accommodation was reviewed.");

    private final String typeMessage;

    NotificationType(String typeMessage) {
        this.typeMessage = typeMessage;
    }

    public String getTypeMessage() {
        return typeMessage;
    }
}
