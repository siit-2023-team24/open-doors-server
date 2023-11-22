package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestDTO;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;

import java.sql.Timestamp;

public class ReservationRequestDTOHostView extends ReservationRequestDTO {
    private int cancelledNumber;

    public ReservationRequestDTOHostView() {
    }

    public ReservationRequestDTOHostView(Long id, String guestUsername, String accommodationName, DateRange dateRange,
                                         int guestNumber, double totalPrice, ReservationRequestStatus status,
                                         Timestamp timestamp, int cancelledNumber) {
        super(id, guestUsername, accommodationName, dateRange, guestNumber, totalPrice, status, timestamp);
        this.cancelledNumber = cancelledNumber;
    }

    public int getCancelledNumber() {
        return cancelledNumber;
    }

    public void setCancelledNumber(int cancelledNumber) {
        this.cancelledNumber = cancelledNumber;
    }
}
