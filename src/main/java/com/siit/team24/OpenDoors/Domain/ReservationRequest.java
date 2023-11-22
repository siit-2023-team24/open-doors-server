package com.siit.team24.OpenDoors.Domain;

import com.siit.team24.OpenDoors.Domain.Enums.ReservationRequestStatus;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;

@Entity
public class ReservationRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Email
    private String guest;
    private Long accommodationId;
    @Embedded
    private DateRange dateRange;
    @Min(1)
    private int guestNumber;
    @Min(0)
    private double totalPrice;
    private ReservationRequestStatus status;

    public ReservationRequest() {
    }

    public ReservationRequest(Long id, String guest, Long accommodationId, DateRange dateRange,
                              int guestNumber, int totalPrice, ReservationRequestStatus status) {
        this.id = id;
        this.guest = guest;
        this.accommodationId = accommodationId;
        this.dateRange = dateRange;
        this.guestNumber = guestNumber;
        this.totalPrice = totalPrice;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getGuest() {
        return guest;
    }

    public void setGuest(String guest) {
        this.guest = guest;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
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

    @Override
    public String toString() {
        return "ReservationRequest{" +
                "id=" + id +
                ", guest='" + guest + '\'' +
                ", accommodationId=" + accommodationId +
                ", dateRange=" + dateRange +
                ", guestNumber=" + guestNumber +
                ", totalPrice=" + totalPrice +
                ", status=" + status +
                '}';
    }
}
