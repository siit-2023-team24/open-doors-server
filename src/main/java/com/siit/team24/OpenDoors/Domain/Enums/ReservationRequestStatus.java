package com.siit.team24.OpenDoors.Domain.Enums;

public enum ReservationRequestStatus {
    PENDING("pending"),
    CONFIRMED("confirmed"),
    DENIED("denied"),
    CANCELED("canceled");

    private final String status;

    ReservationRequestStatus(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }
}
