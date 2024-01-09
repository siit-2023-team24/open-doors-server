package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.userManagement.*;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import com.siit.team24.OpenDoors.service.user.AccountService;
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

    UserSummaryDTO testUserSummaryDTO = new UserSummaryDTO(
            "bob@testmail.me", "Bob", "Roberts", "host"
    );

    NotificationDTO testNotificationDTO = new NotificationDTO(
            "You have a new review.", "Excellent", new Timestamp(98423)
    );

//    AccommodationSearchDTO testAccommodationSearchDTO = new AccommodationSearchDTO(
//            (long)463453243, (long)363543252, "Hotel Park", 4.5, 340, true,
//            "Novi Sad", "Serbia"
//    );




    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @PutMapping(consumes = "multipart/form-data")
    public ResponseEntity<Void> updateUser(UserFormDataDTO data) {
        UserEditedDTO dto = null;
        UserEditedDTO userDTO = data.toEditedDTO();

        System.out.println(userDTO);
        try {
            dto = service.update(userDTO);
            System.out.println("Edited user: " + userDTO.getId());
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (IOException e) {
            System.err.println("Error updating user: " + userDTO.getId());
            e.printStackTrace();
            return  new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @PutMapping(consumes = "application/json", value = "/new-password")
    public ResponseEntity<Void> updateAccount(@RequestBody NewPasswordDTO newPasswordDTO){
        try {
            this.service.changePassword(newPasswordDTO);
        } catch (Exception e) {
            System.err.println("Error changing password for: " + newPasswordDTO.getUsername());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        service.delete(id);
        pendingAccommodationService.deleteAllForHost(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping(value = "/all")
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        //todo
        List<UserSummaryDTO> users = new ArrayList<>();
        users.add(testUserSummaryDTO);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getUsersPage(
            Pageable pageable) {
        //todo
        List<UserSummaryDTO> users = new ArrayList<>();
        users.add(testUserSummaryDTO);
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

//    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN') or hasRole('GUEST')")
    @GetMapping(value = "/{userId}/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        //todo
        List<NotificationDTO> notifications = new ArrayList<>();
        notifications.add(testNotificationDTO);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('GUEST')")
    @GetMapping(value="/{userId}/favorites")
    public ResponseEntity<List<AccommodationSearchDTO>> getFavoritesByUserId(@PathVariable Long userId) {
        //todo
        List<AccommodationSearchDTO> favorites = new ArrayList<>();
        // favorites.add(testAccommodationSearchDTO);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }

//    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping(value = "/{userId}/status")
    public ResponseEntity<Void> changeBlockStatus(@PathVariable Long userId,
                                                  @RequestParam boolean isBlocked){
        //todo
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
