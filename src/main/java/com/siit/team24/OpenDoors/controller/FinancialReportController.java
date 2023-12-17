package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.financialReport.AccommodationFinancialReportItemDTO;
import com.siit.team24.OpenDoors.dto.financialReport.FinancialReportDateRangeItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/financial-report")
public class FinancialReportController {

    FinancialReportDateRangeItemDTO testFinancialReportDateRangeItemDTO = new FinancialReportDateRangeItemDTO(
            "Hotel Plaza", (long)348734384, 5, 52200.5
    );

    AccommodationFinancialReportItemDTO testAccommodationFinancialReportItemDTO = new AccommodationFinancialReportItemDTO(
            3, 64600.0, 11
    );
    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<List<FinancialReportDateRangeItemDTO>> getDateRangeReport(
            @PathVariable Long hostId,
            @RequestParam(name = "start", required = true)String start,
            @RequestParam(name = "end", required = true)String end) {

        List<FinancialReportDateRangeItemDTO> items = new ArrayList<>();
        items.add(testFinancialReportDateRangeItemDTO);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/accommodation/{accommodationId}")
    public ResponseEntity<List<AccommodationFinancialReportItemDTO>> getAccommodationReport(
            @PathVariable Long accommodationId,
            @RequestParam(name = "year", required = true) int year) {

        List<AccommodationFinancialReportItemDTO> items = new ArrayList<>();
        items.add(testAccommodationFinancialReportItemDTO);
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
