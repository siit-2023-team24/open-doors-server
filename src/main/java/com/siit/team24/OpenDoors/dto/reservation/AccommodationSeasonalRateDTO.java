package com.siit.team24.OpenDoors.dto.reservation;

import org.mockito.internal.verification.Times;

import java.sql.Timestamp;

public class AccommodationSeasonalRateDTO {

    private Long accommodationId;
    private Timestamp startDate;
    private Timestamp endDate;

    public AccommodationSeasonalRateDTO(Long accommodationId, Timestamp startDate, Timestamp endDate) {
        this.accommodationId = accommodationId;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
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

    @Override
    public String toString() {
        return "AccommodationSeasonalRateDTO{" +
                "accommodationId=" + accommodationId +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
