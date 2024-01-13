package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.userManagement.NewUserReportDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.model.UserReport;
import com.siit.team24.OpenDoors.service.user.UserReportService;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/user-reports")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;



    @GetMapping
    public ResponseEntity<List<UserReportDTO>> getAllUserReports() {
        List<UserReportDTO> reports = new ArrayList<>();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @GetMapping(value="/{id}")
    public ResponseEntity<List<String>> getReportableUsersForUser(@PathVariable Long id,
                        @RequestParam boolean isGuestComplainant) {
        List<String> reportableUserIds = userReportService.getReportableUsers(id, isGuestComplainant);
        return new ResponseEntity<>(reportableUserIds, HttpStatus.OK);
    }

    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> createUserReport(@RequestBody NewUserReportDTO dto) {
        UserReport report = userReportService.createReport(dto);
        UserReportDTO returnDto = new UserReportDTO(report);
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        System.out.println(returnDto.toString());
        System.out.println("AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAa");
        return new ResponseEntity<>(returnDto, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> updateReport(@RequestBody UserReportDTO userReportDTO){
        return new ResponseEntity<>(userReportDTO, HttpStatus.OK);
    }
}
