package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.userManagement.*;
import com.siit.team24.OpenDoors.model.Account;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("open-doors/users")
public class UserController {
    @PostMapping(consumes="application/json", value = "/login")
    public ResponseEntity<UserDTO> login(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(new UserDTO(),HttpStatus.CREATED);
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<UserAccountDTO> createUser(@RequestBody UserAccountDTO registerDTO) {
        return new ResponseEntity<>(new UserAccountDTO(), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(new UserDTO(), HttpStatus.OK);
    }
    @PutMapping(consumes = "application/json", value = "/newPassword")
    public ResponseEntity<Void> updateAccount(@RequestBody NewPasswordDTO newPasswordDTO){
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        return new ResponseEntity<>(new ArrayList<UserSummaryDTO>(), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getUsersPage(
            Pageable pageable) {
        return new ResponseEntity<>(new ArrayList<UserSummaryDTO>(), HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserAccountDTO> getUser(
            @PathVariable Long id) {
        return new ResponseEntity<>(new UserAccountDTO(), HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationDTO> notifications = new ArrayList<>();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(value="/{userId}/favorites")
    public ResponseEntity<List<AccommodationSearchDTO>> getFavoritesByUserId(@PathVariable Long userId) {
        List<AccommodationSearchDTO> favorites = new ArrayList<>();
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }
 
    @PutMapping(value = "/{userId}/status")
    public ResponseEntity<Void> changeBlockStatus(@PathVariable Long userId,
                                                  @RequestBody boolean isBlocked){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
