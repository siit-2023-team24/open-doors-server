package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;

import java.sql.Timestamp;

public class ReservationRequestDTO {

    protected Long id;
    protected String guestUsername;
    protected String accommodationName;
    protected DateRange dateRange;
    protected int guestNumber;
    protected double totalPrice;
    protected ReservationRequestStatus status;
    protected Timestamp timestamp;

    public ReservationRequestDTO() {
    }

    public ReservationRequestDTO(Long id, String guestUsername, String accommodationName, DateRange dateRange,
                                 int guestNumber, double totalPrice, ReservationRequestStatus status, Timestamp timestamp) {
        this.id = id;
        this.guestUsername = guestUsername;
        this.accommodationName = accommodationName;
        this.dateRange = dateRange;
        this.guestNumber = guestNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.timestamp = timestamp;
    }

    public Long getId() {
        return id;
    }

    public String getGuest() {
        return guestUsername;
    }

    public void setGuest(String guestUsername) {
        this.guestUsername = guestUsername;
    }

    public String getAccommodation() {
        return accommodationName;
    }

    public void setAccommodation(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public DateRange getDateRange() {
        return dateRange;
    }

    public void setDateRange(DateRange dateRange) {
        this.dateRange = dateRange;
    }

    public int getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public ReservationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationRequestStatus status) {
        this.status = status;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

}
