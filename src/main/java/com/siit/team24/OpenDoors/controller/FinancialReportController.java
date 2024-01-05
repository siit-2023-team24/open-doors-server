package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.financialReport.AccommodationFinancialReportItemDTO;
import com.siit.team24.OpenDoors.dto.financialReport.DateRangeReportDTO;
import com.siit.team24.OpenDoors.dto.financialReport.DateRangeReportParamsDTO;
import com.siit.team24.OpenDoors.service.FinancialReportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/financialReport")
public class FinancialReportController {

    @Autowired
    FinancialReportService financialReportService;

    @PreAuthorize("hasRole('HOST')")
    @PostMapping(value = "/dateRangeReports")
    public ResponseEntity<List<DateRangeReportDTO>> getDateRangeReport(@RequestBody DateRangeReportParamsDTO reportParams) {
        List<DateRangeReportDTO> reports = financialReportService.getDateRangeReports(reportParams.getHostId(), reportParams.getStartDate(), reportParams.getEndDate());

        return new ResponseEntity<>(reports, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/accommodation/{accommodationId}")
    public ResponseEntity<List<AccommodationFinancialReportItemDTO>> getAccommodationReport(
            @PathVariable Long accommodationId,
            @RequestParam(name = "year", required = true) int year) {

        List<AccommodationFinancialReportItemDTO> items = new ArrayList<>();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
