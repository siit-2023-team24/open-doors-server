package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.DateRange;

import java.time.LocalDate;

public class DateRangeService {

    public static boolean areOverlapping(DateRange range1, DateRange range2) {
        return !range1.getEndDate().isBefore(range2.getStartDate()) &&
                !range2.getEndDate().isBefore(range1.getStartDate());
    }

    public static boolean IsDateWithinRange(LocalDate date, DateRange range) {
        return !date.isBefore(range.getStartDate()) && !date.isAfter(range.getEndDate());
    }
}
