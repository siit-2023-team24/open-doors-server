package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.financialReport.AccommodationIdReportDTO;
import com.siit.team24.OpenDoors.dto.financialReport.DateRangeReportDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

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

        List<AccommodationIdReportDTO> report = new ArrayList<>();
        LocalDate currentDate = LocalDate.now();

        for (int i = 12; i > 0; i--) {
            LocalDate monthStart = currentDate.minusMonths(i).withDayOfMonth(1);
            LocalDate monthEnd = monthStart.withDayOfMonth(monthStart.lengthOfMonth());

            Timestamp startDate = Timestamp.valueOf(monthStart.atStartOfDay());
            Timestamp endDate = Timestamp.valueOf(monthEnd.atTime(23, 59, 59));

            double[] results = getNumOfSuccessfulAndProfitForAccommodationInDateRange(accommodationId, startDate, endDate);

            int numOfReservations = (int) results[0];
            double profit = results[1];

            String month = monthStart.format(DateTimeFormatter.ofPattern("MMMM"));
            AccommodationIdReportDTO reportDTO = new AccommodationIdReportDTO(month, numOfReservations, profit);
            report.add(reportDTO);
        }

        return report;
    }

    public void exportDateRangeReport(Long hostId, Timestamp startDate, Timestamp endDate) {
        List<DateRangeReportDTO> report = getDateRangeReports(hostId, startDate, endDate);
        File file;
        try {
            file = ResourceUtils.getFile("classpath:dateRangeReport.jrxml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JasperReport jasperReport;
        try {
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(report);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Open Doors");
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint,
            "C:\\Users\\milic\\Downloads\\dateRangeReport_" + hostId + "_" +
                    startDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) + "_" +
                    endDate.toLocalDateTime().format(DateTimeFormatter.ofPattern("yyyy-MM-dd")) +  ".pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }

    public void exportAccommodationIdReport(Long accommodationId) {
        List<AccommodationIdReportDTO> report = getAccommodationIdReport(accommodationId);
        File file;
        try {
            file = ResourceUtils.getFile("classpath:accommodationIdReport.jrxml");
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
        JasperReport jasperReport;
        try {
            jasperReport = JasperCompileManager.compileReport(file.getAbsolutePath());
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        JRBeanCollectionDataSource dataSource = new JRBeanCollectionDataSource(report);
        Map<String, Object> parameters = new HashMap<>();
        parameters.put("createdBy", "Open Doors");
        JasperPrint jasperPrint;
        try {
            jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, dataSource);
        } catch (JRException e) {
            throw new RuntimeException(e);
        }

        try {
            JasperExportManager.exportReportToPdfFile(jasperPrint,
                    "C:\\Users\\milic\\Downloads\\accommodationIdReport_" + accommodationId +  ".pdf");
        } catch (JRException e) {
            throw new RuntimeException(e);
        }
    }
}
