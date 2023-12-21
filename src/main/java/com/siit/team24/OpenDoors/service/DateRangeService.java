package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.DateRange;

import java.sql.Timestamp;

public class DateRangeService {

    public static boolean areOverlapping(DateRange range1, DateRange range2) {
        return !range1.getEndDate().before(range2.getStartDate()) &&
                !range2.getEndDate().before(range1.getStartDate());
    }

    public static boolean isDateWithinRange(Timestamp date, DateRange range) {
        long dateMillis = date.getTime();
        long startMillis = range.getStartDate().getTime();
        long endMillis = range.getEndDate().getTime();

        return dateMillis >= startMillis && dateMillis <= endMillis;
    }
}

