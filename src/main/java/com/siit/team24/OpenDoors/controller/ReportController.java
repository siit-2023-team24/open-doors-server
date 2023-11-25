package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.UserReportDTO;
import com.siit.team24.OpenDoors.dto.UserSummaryDTO;
import com.siit.team24.OpenDoors.model.UserReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
public class ReportController {
    @GetMapping
    public ResponseEntity<List<UserReportDTO>> getAllUserReports() {
        return new ResponseEntity<>(new ArrayList<UserReportDTO>(), HttpStatus.OK);
    }

    @PutMapping
    public ResponseEntity<UserReportDTO> updateReport(@RequestBody UserReportDTO userReportDTO){
        return new ResponseEntity<>(new UserReportDTO(new UserReport()), HttpStatus.OK);
    }
}