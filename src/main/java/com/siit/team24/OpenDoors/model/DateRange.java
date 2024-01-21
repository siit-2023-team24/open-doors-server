package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;

import java.sql.Timestamp;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import java.util.ArrayList;
import java.util.List;
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

    public int getNumberOfNights() {
        long millisPerDay = 24 * 60 * 60 * 1000; // Number of milliseconds in a day
        long startMillis = startDate.getTime();
        long endMillis = endDate.getTime();

        return (int) ((endMillis - startMillis) / millisPerDay + 1);
    }

    public List<Timestamp> getTimestampRange() {
        long millisPerDay = 24 * 60 * 60 * 1000; // 1 day
        List<Timestamp> range = new ArrayList<>();

        // Set the initial timestamp to the start timestamp
        Instant currentInstant = startDate.toInstant();

        // Iterate while the current timestamp is before or equal to the end timestamp
        while (!currentInstant.isAfter(endDate.toInstant())) {
            // Add the current timestamp to the list
            range.add(Timestamp.from(currentInstant));

            // Increment the current timestamp by the specified interval
            currentInstant = currentInstant.plusMillis(millisPerDay);
        }

        return range;
    }

    public boolean contains(DateRange other) {
        boolean isStartDateBeforeOrEqual = this.startDate.equals(other.startDate) || this.startDate.before(other.startDate);
        boolean isEndDateAfterOrEqual = this.endDate.equals(other.endDate) || this.endDate.after(other.endDate);

        return isStartDateBeforeOrEqual && isEndDateAfterOrEqual;
    }

    public boolean contains(Timestamp date) {
        return !date.before(this.startDate) && !date.after(this.endDate);
    }

    public boolean overlapsWith(DateRange other) {
        if (other.startDate.compareTo(this.startDate) >= 0 && other.startDate.compareTo(this.endDate) <= 0)
            return true;
        if (startDate.compareTo(other.startDate) >= 0 && startDate.compareTo(other.endDate) <= 0)
            return true;
        return false;
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
