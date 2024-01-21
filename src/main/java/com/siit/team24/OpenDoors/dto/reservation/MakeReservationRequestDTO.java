package com.siit.team24.OpenDoors.dto.reservation;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;

import java.sql.Timestamp;

public class MakeReservationRequestDTO {
    Long accommodationId;
    Long guestId;
    @FutureOrPresent
    Timestamp startDate;
    @FutureOrPresent
    Timestamp endDate;
    @Min(1)
    int numberOfGuests;
    @Min(0)
    Double totalPrice;

    public MakeReservationRequestDTO() {}

    public MakeReservationRequestDTO(Long accommodationId, Long guestId, Timestamp startDate, Timestamp endDate, int numberOfGuests, Double totalPrice) {
        this.accommodationId = accommodationId;
        this.guestId = guestId;
        this.startDate = startDate;
        this.endDate = endDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
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

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    @Override
    public String toString() {
        return "MakeReservationRequestDTO{" +
                "accommodationId=" + accommodationId +
                ", guestId=" + guestId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", numberOfGuests=" + numberOfGuests +
                ", totalPrice=" + totalPrice +
                '}';
    }
}
