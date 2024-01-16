package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import jakarta.persistence.EntityNotFoundException;

import java.sql.Timestamp;

public class ReservationRequestForHostDTO{

    protected Long id;
    protected String guestUsername;
    protected String accommodationName;
    protected Timestamp startDate;
    protected Timestamp endDate;
    protected int guestNumber;
    protected double totalPrice;
    protected ReservationRequestStatus status;
    protected Timestamp timestamp;
    private int cancelledNumber;

    public ReservationRequestForHostDTO() {
    }

    public ReservationRequestForHostDTO(Long id, String guestUsername, String accommodationName, Timestamp startDate,
                                        Timestamp endDate, int guestNumber, double totalPrice, ReservationRequestStatus status,
                                        Timestamp timestamp, int cancelledNumber) {
        this.id = id;
        this.guestUsername = guestUsername;
        this.accommodationName = accommodationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.guestNumber = guestNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.timestamp = timestamp;
        this.cancelledNumber = cancelledNumber;
    }

    public ReservationRequestForHostDTO(ReservationRequest request) {
        try {
            accommodationName = request.getAccommodation().getUniqueName();
        } catch (EntityNotFoundException e) {
            request.setAccommodation(new Accommodation());
        }
        this.id = request.getId();
        this.guestUsername = request.getGuest().getUsername();
        this.accommodationName = request.getAccommodation().getUniqueName();
        this.startDate = request.getDateRange().getStartDate();
        this.endDate = request.getDateRange().getEndDate();
        this.guestNumber = request.getGuestNumber();
        this.totalPrice = request.getTotalPrice();
        this.status = request.getStatus();
        this.timestamp = request.getTimestamp();
        this.cancelledNumber = 0;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuestUsername() {
        return guestUsername;
    }

    public void setGuestUsername(String guestUsername) {
        this.guestUsername = guestUsername;
    }

    public String getAccommodationName() {
        return accommodationName;
    }

    public void setAccommodationName(String accommodationName) {
        this.accommodationName = accommodationName;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
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

    public int getCancelledNumber() {
        return cancelledNumber;
    }

    public void setCancelledNumber(int cancelledNumber) {
        this.cancelledNumber = cancelledNumber;
    }

    @Override
    public String toString() {
        return "ReservationRequestForHostDTO{" +
                "id=" + id +
                ", guestUsername='" + guestUsername + '\'' +
                ", accommodationName='" + accommodationName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", guestNumber=" + guestNumber +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                ", timestamp=" + timestamp +
                ", cancelledNumber=" + cancelledNumber +
                '}';
    }
}
