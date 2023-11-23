package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.financialReport.AccommodationFinancialReportItemDTO;
import com.siit.team24.OpenDoors.dto.financialReport.FinancialReportDateRangeItemDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/financial-report")
public class FinancialReportController {

    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<List<FinancialReportDateRangeItemDTO>> getDateRangeReport(
            @PathVariable Long hostId,
            @RequestParam(name = "start", required = true)LocalDate start,
            @RequestParam(name = "end", required = true)LocalDate end) {

        List<FinancialReportDateRangeItemDTO> items = new ArrayList<>();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }

    @GetMapping(value = "/accommodation/{accommodationId}")
    public ResponseEntity<List<AccommodationFinancialReportItemDTO>> getAccommodationReport(
            @PathVariable Long accommodationId,
            @RequestParam(name = "year", required = true) int year) {

        List<AccommodationFinancialReportItemDTO> items = new ArrayList<>();
        return new ResponseEntity<>(items, HttpStatus.OK);
    }
}
