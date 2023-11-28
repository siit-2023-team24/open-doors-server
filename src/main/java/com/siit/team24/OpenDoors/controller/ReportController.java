package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO;
import com.siit.team24.OpenDoors.model.UserReport;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/user-reports")
public class ReportController {
    @GetMapping
    public ResponseEntity<List<UserReportDTO>> getAllUserReports() {
        return new ResponseEntity<>(new ArrayList<UserReportDTO>(), HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> createUserReport() {
        return new ResponseEntity<>(new UserReportDTO(new UserReport()), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> updateReport(@RequestBody UserReportDTO userReportDTO){
        return new ResponseEntity<>(new UserReportDTO(new UserReport()), HttpStatus.OK);
    }
}
