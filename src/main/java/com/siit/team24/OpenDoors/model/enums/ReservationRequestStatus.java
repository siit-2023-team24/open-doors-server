package com.siit.team24.OpenDoors.model.enums;

public enum ReservationRequestStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    DENIED("denied"),
    CANCELLED("cancelled");

    private final String status;

    ReservationRequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}