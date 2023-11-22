package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;

import java.sql.Timestamp;

public class ReservationRequestForGuestDTO {
    protected Long id;
    protected String accommodationName;
    protected DateRange dateRange;
    protected int guestNumber;
    protected double totalPrice;
    protected ReservationRequestStatus status;
    protected Timestamp timestamp;

    public ReservationRequestForGuestDTO() {
    }

    public ReservationRequestForGuestDTO(Long id, String accommodationName, DateRange dateRange, int guestNumber, double totalPrice, ReservationRequestStatus status, Timestamp timestamp) {
        this.id = id;
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

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
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
