package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;

import java.sql.Timestamp;

public class ReservationRequestForHostDTO{
    private int cancelledNumber;

    public ReservationRequestForHostDTO() {
    }

    public ReservationRequestForHostDTO(Long id, String guestUsername, String accommodationName, DateRange dateRange,
                                        int guestNumber, double totalPrice, ReservationRequestStatus status,
                                        Timestamp timestamp, int cancelledNumber) {
        this.cancelledNumber = cancelledNumber;
    }

    public int getCancelledNumber() {
        return cancelledNumber;
    }

    public void setCancelledNumber(int cancelledNumber) {
        this.cancelledNumber = cancelledNumber;
    }
}
