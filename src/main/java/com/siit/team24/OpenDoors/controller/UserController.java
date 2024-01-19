package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.userManagement.*;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.model.UserReport;
import com.siit.team24.OpenDoors.service.AccommodationReviewService;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import com.siit.team24.OpenDoors.service.user.AccountService;
import com.siit.team24.OpenDoors.service.user.UserReportService;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("open-doors/users")
public class UserController {

    @Autowired
    private UserService service;

    @Autowired
    private PendingAccommodationService pendingAccommodationService;

    @Autowired
    private UserReportService userReportService;

    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<UserDTO> updateUser(UserFormDataDTO data) {
        UserDTO dto = null;
        UserEditedDTO userDTO = data.toEditedDTO();

        System.out.println(userDTO);
        try {
            dto = service.update(userDTO);
            System.out.println("Edited user: " + userDTO.getId());
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("Error updating user: " + userDTO.getId());
            e.printStackTrace();
            return  new ResponseEntity<>(dto, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @PutMapping(consumes = "application/json", value = "/new-password")
    public ResponseEntity<Void> updateAccount(@RequestBody NewPasswordDTO newPasswordDTO){
        this.service.changePassword(newPasswordDTO);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        pendingAccommodationService.deleteAllForHost(id);
        userReportService.deleteAllFor(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/blocked")
    public ResponseEntity<List<UserSummaryDTO>> getBlockedUsers() {
        List<UserSummaryDTO> users = service.getBlockedDTOs();
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @GetMapping(value = "/{id}")
    public ResponseEntity<UserAccountViewDTO> getUser(
            @PathVariable Long id) {
        try {
            User user = service.findById(id);
            return new ResponseEntity<>(user.toAccountViewDTO(), HttpStatus.OK);
        }
        catch (EntityNotFoundException e) {
            System.err.println("Person with id " + id + " not found in database.");
            return new ResponseEntity<>(new UserAccountViewDTO(), HttpStatus.BAD_REQUEST);
        }

    }

    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @GetMapping(value = "/{userId}/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        //todo
        List<NotificationDTO> notifications = new ArrayList<>();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/unblock/{id}")
    public ResponseEntity<Void> unblock(@PathVariable Long id){
        service.unblock(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
