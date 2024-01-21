package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.financialReport.AccommodationIdReportDTO;
import com.siit.team24.OpenDoors.dto.financialReport.DateRangeReportDTO;
import com.siit.team24.OpenDoors.dto.financialReport.DateRangeReportParamsDTO;
import com.siit.team24.OpenDoors.service.FinancialReportService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/financialReport")
public class FinancialReportController {

    @Autowired
    FinancialReportService financialReportService;

    @PreAuthorize("hasRole('HOST')")
    @PostMapping(value = "/dateRangeReport")
    public ResponseEntity<List<DateRangeReportDTO>> getDateRangeReport(@Valid @RequestBody DateRangeReportParamsDTO reportParams) {
        List<DateRangeReportDTO> report = financialReportService.getDateRangeReports(reportParams.getHostId(), reportParams.getStartDate(), reportParams.getEndDate());

        return new ResponseEntity<>(report, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/accommodationIdReport/{accommodationId}")
    public ResponseEntity<List<AccommodationIdReportDTO>> getAccommodationIdReport(@PathVariable Long accommodationId) {
        List<AccommodationIdReportDTO> report = financialReportService.getAccommodationIdReport(accommodationId);
        return new ResponseEntity<>(report, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @PostMapping(value = "/dateRangeReport/export")
    public ResponseEntity<Void> getDateRangeReportExport(@Valid @RequestBody DateRangeReportParamsDTO reportParams) {
        financialReportService.exportDateRangeReport(reportParams.getHostId(), reportParams.getStartDate(), reportParams.getEndDate());

        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @PostMapping(value = "/accommodationIdReport/export")
    public ResponseEntity<Void> getAccommodationIdReportExport(@RequestBody Long accommodationId) {
        financialReportService.exportAccommodationIdReport(accommodationId);

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
