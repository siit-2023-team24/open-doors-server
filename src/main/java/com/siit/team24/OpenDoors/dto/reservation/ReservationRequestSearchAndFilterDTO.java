package com.siit.team24.OpenDoors.dto.reservation;

import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;

import java.sql.Timestamp;

public class ReservationRequestSearchAndFilterDTO {

    String accommodationName;
    Timestamp startDate;
    Timestamp endDate;
    ReservationRequestStatus status;

    public ReservationRequestSearchAndFilterDTO(String accommodationName, Timestamp startDate, Timestamp endDate, ReservationRequestStatus status) {
        this.accommodationName = accommodationName;
        this.startDate = startDate;
        this.endDate = endDate;
        this.status = status;
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

    public ReservationRequestStatus getStatus() {
        return status;
    }

    public void setStatus(ReservationRequestStatus status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "ReservationRequestSearchAndFilterDTO{" +
                "accommodationName='" + accommodationName + '\'' +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", status=" + status +
                '}';
    }
}
