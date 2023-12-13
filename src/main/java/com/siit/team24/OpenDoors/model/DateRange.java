package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.sql.Timestamp;
import java.util.Objects;

@Embeddable
public class DateRange {
    @Column(name = "startDate", nullable = false)
    private Timestamp startDate;
    @Column(name = "endDate", nullable = false)
    private Timestamp endDate;

    public DateRange(Timestamp startDate, Timestamp endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }

    public DateRange() {}

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
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof DateRange dateRange)) return false;
        return Objects.equals(getStartDate(), dateRange.getStartDate()) && Objects.equals(getEndDate(), dateRange.getEndDate());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getStartDate(), getEndDate());
    }

    @Override
    public String toString() {
        return "DateRange{" +
                "startDate=" + startDate +
                ", endDate=" + endDate +
                '}';
    }
}
