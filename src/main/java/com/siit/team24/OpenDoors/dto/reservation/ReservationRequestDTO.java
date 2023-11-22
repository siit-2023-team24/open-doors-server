package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;

import java.sql.Timestamp;

public class ReservationRequestDTO extends ReservationRequestForGuestDTO{

    protected String guestUsername;

    public ReservationRequestDTO() {
    }

    public ReservationRequestDTO(Long id, String guestUsername, String accommodationName, DateRange dateRange,
                                 int guestNumber, double totalPrice, ReservationRequestStatus status, Timestamp timestamp) {
        super(id, accommodationName, dateRange, guestNumber, totalPrice, status, timestamp);
        this.guestUsername = guestUsername;
    }

    public String getGuest() {
        return guestUsername;
    }

    public void setGuest(String guestUsername) {
        this.guestUsername = guestUsername;
    }


}
