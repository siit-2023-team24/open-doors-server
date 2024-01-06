package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;

import java.sql.Time;
import java.sql.Timestamp;

public class ReservationRequestForGuestDTO {
    protected Long id;
    protected Long imageId;
    protected String accommodationName;
    protected Timestamp startDate;
    protected Timestamp endDate;
    protected int guestNumber;
    protected double totalPrice;
    protected ReservationRequestStatus status;
    protected Timestamp timestamp;

    public ReservationRequestForGuestDTO() {
    }

    public ReservationRequestForGuestDTO(Long id, Long imageId, String accommodationName, DateRange dateRange, int guestNumber, double totalPrice, ReservationRequestStatus status, Timestamp timestamp) {
        this.id = id;
        this.imageId = imageId;
        this.accommodationName = accommodationName;
        this.startDate = dateRange.getStartDate();
        this.endDate = dateRange.getEndDate();
        this.guestNumber = guestNumber;
        this.totalPrice = totalPrice;
        this.status = status;
        this.timestamp = timestamp;
    }

    public ReservationRequestForGuestDTO(ReservationRequest request) {
        this(   request.getId(),
                null,
                request.getAccommodation().getName(),
                request.getDateRange(),
                request.getGuestNumber(),
                request.getTotalPrice(),
                request.getStatus(),
                request.getTimestamp());
        if (!request.getAccommodation().getImages().isEmpty())
            imageId = ((Image)request.getAccommodation().getImages().toArray()[0]).getId();
    }

    public Long getId() {
        return id;
    }

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long imageId) {
        this.imageId = imageId;
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
}
