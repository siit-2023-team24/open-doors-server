package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.financialReport.AccommodationIdReportDTO;
import com.siit.team24.OpenDoors.dto.financialReport.DateRangeReportDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
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
            double[] stats = getNumOfSuccessfulAndProfitForAccommodationInDateRange(accommodation.getId(), startDate, endDate);
            DateRangeReportDTO report = new DateRangeReportDTO(accommodation.getId(), accommodation.getName(), (int) stats[0], stats[1]);
            reports.add(report);
        }
        return reports;
    }

    private double[] getNumOfSuccessfulAndProfitForAccommodationInDateRange(Long accommodationId, Timestamp startDate, Timestamp endDate) {
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

    public List<AccommodationIdReportDTO> getAccommodationIdReport(Long accommodationId) {
        List<String> months = new ArrayList<>(Arrays.asList(
                "January", "February", "March", "April", "May", "June",
                "July", "August", "September", "October", "November", "December"
        ));

        List<AccommodationIdReportDTO> report = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 12; i > 0; i--) {
            LocalDate monthStart = currentDate.minusMonths(i).withDayOfMonth(1);
            LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

            Timestamp startDate = Timestamp.valueOf(monthStart.atStartOfDay());
            Timestamp endDate = Timestamp.valueOf(monthEnd.atTime(23, 59, 59));

            double[] results = getNumOfSuccessfulAndProfitForAccommodationInDateRange(accommodationId, startDate, endDate);

            int numOfReservations = (int) results[0];
            int profit = (int) results[1];

            String month = monthStart.format(DateTimeFormatter.ofPattern("MMMM"));
            AccommodationIdReportDTO reportDTO = new AccommodationIdReportDTO(month, numOfReservations, profit);
            report.add(reportDTO);
        }

        return report;
    }
}
