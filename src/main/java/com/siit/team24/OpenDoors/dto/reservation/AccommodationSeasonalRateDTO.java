package com.siit.team24.OpenDoors.dto.reservation;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import org.mockito.internal.verification.Times;

import java.sql.Timestamp;

public class AccommodationSeasonalRateDTO {
    @Min(1)
    private Long accommodationId;
    @FutureOrPresent
    private Timestamp startDate;
    @FutureOrPresent
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
