package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.financialReport.DateRangeReportDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@Service
public class FinancialReportService {

    @Autowired
    AccommodationService accommodationService;

    @Autowired
    ReservationRequestService reservationRequestService;

    public List<DateRangeReportDTO> getDateRangeReports(Long hostId, Timestamp startDate, Timestamp endDate) {
        List<DateRangeReportDTO> reports = new ArrayList<>();
        List<Accommodation> accommodations = accommodationService.findAllByHostId(hostId);
        for(Accommodation accommodation :  accommodations) {
            double[] stats = getNumOfSuccessfulForAccommodationInDateRange(accommodation.getId(), startDate, endDate);
            DateRangeReportDTO report = new DateRangeReportDTO(accommodation.getId(), accommodation.getName(), (int) stats[0], stats[1]);
            reports.add(report);
        }
        return reports;
    }

    private double[] getNumOfSuccessfulForAccommodationInDateRange(Long accommodationId, Timestamp startDate, Timestamp endDate) {
        List<ReservationRequest> allRequests = reservationRequestService.findAll();
        double numOfSuccessful = 0.0;
        double profit = 0.0;

        for(ReservationRequest request : allRequests) {
            Timestamp today = new Timestamp(System.currentTimeMillis());
            Timestamp todayPlusDeadline = Timestamp.valueOf(today.toLocalDateTime().plusDays(request.getAccommodation().getDeadline()));

            if(request.getAccommodation().getId().equals(accommodationId) &&
                    request.getStatus() == ReservationRequestStatus.CONFIRMED &&
                    request.getDateRange().getStartDate().after(startDate) && request.getDateRange().getStartDate().before(endDate) &&
                    request.getDateRange().getStartDate().before(todayPlusDeadline)
            ) {
                numOfSuccessful += 1;
                profit += request.getTotalPrice();
            }
        }
        double[] numOfSuccessfulAndProfit = new double[2];

        numOfSuccessfulAndProfit[0] = numOfSuccessful;
        numOfSuccessfulAndProfit[1] = profit;

        return numOfSuccessfulAndProfit;
    }
}
