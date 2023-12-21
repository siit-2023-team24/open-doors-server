package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO;
import com.siit.team24.OpenDoors.model.UserReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/user-reports")
public class ReportController {
    UserReportDTO testUserReportDTO = new UserReportDTO(
            (long)32949273, "repo@rted.me", "plain@ant.com",
            new Timestamp(327428), "Disgusting manners", "active"
    );
    @GetMapping
    public ResponseEntity<List<UserReportDTO>> getAllUserReports() {
        List<UserReportDTO> reports = new ArrayList<>();
        reports.add(testUserReportDTO);
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> createUserReport() {
        return new ResponseEntity<>(testUserReportDTO, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> updateReport(@RequestBody UserReportDTO userReportDTO){
        return new ResponseEntity<>(testUserReportDTO, HttpStatus.OK);
    }
}
