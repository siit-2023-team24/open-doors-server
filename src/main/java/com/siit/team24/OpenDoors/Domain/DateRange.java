package com.siit.team24.OpenDoors.Domain;

import jakarta.persistence.Embeddable;
import java.time.LocalDate;

@Embeddable
public class DateRange {
    private LocalDate startDate;
    private LocalDate endDate;

    public DateRange(LocalDate startDate, LocalDate endDate) {
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
