package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.userManagement.NewUserReportDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.model.UserReport;
import com.siit.team24.OpenDoors.service.user.UserReportService;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "open-doors/user-reports")
public class UserReportController {

    @Autowired
    private UserReportService userReportService;

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserReportDTO>> getAllUserReports() {
        List<UserReportDTO> reports = userReportService.findAllDTOs();
        return new ResponseEntity<>(reports, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST') or hasRole('GUEST')")
    @GetMapping(value="/{id}")
    public ResponseEntity<List<String>> getReportableUsersForUser(@PathVariable Long id,
                        @RequestParam boolean isGuestComplainant) {
        List<String> reportableUserIds = userReportService.getReportableUsers(id, isGuestComplainant);
        return new ResponseEntity<>(reportableUserIds, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST') or hasRole('GUEST')")
    @PostMapping(consumes = "application/json")
    public ResponseEntity<UserReportDTO> createUserReport(@Valid @RequestBody NewUserReportDTO dto) {
        UserReport report = userReportService.createReport(dto);
        UserReportDTO returnDto = new UserReportDTO(report);
        System.out.println(returnDto);
        return new ResponseEntity<>(returnDto, HttpStatus.CREATED);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/dismiss/{id}")
    public ResponseEntity<UserReportDTO> dismiss(@PathVariable Long id) {
        UserReportDTO dto = userReportService.dismiss(id);
        return new ResponseEntity<>(dto, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/resolve/{id}")
    public ResponseEntity<Void> resolve(@PathVariable Long id) {
        userReportService.resolve(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
