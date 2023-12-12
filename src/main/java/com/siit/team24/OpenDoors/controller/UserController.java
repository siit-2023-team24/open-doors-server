package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.userManagement.*;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("open-doors/users")
public class UserController {
    UserDTO testUserDTO = new UserDTO(
            (long)923732, "Steve", "Stevens", "2142365516", "Pennsylvania Avenue", 1,
            "Washington", "United States", (long)2398423
    );

    AccountDTO testAccountDTO = new AccountDTO(
            "steve@testnmail.me", "St3v3St3v3ns"
    );

    UserAccountDTO testUserAccountDTO = new UserAccountDTO(
            (long)923732, "Steve", "Stevens", "2142365516", "Pennsylvania Avenue", 1,
            "Washington", "United States", (long)2398423, "steve@testnmail.me", "St3v3St3v3ns", "guest"
    );

    UserSummaryDTO testUserSummaryDTO = new UserSummaryDTO(
            "bob@testmail.me", "Bob", "Roberts", "host"
    );

    UserAccountViewDTO testUserAccountViewDTO = new UserAccountViewDTO(
            (long)923732, "Steve", "Stevens", "2142365516", "Pennsylvania Avenue", 1,
            "Washington", "United States", (long)2398423, "steve@testnmail.me", "guest"
    );

    NotificationDTO testNotificationDTO = new NotificationDTO(
            "You have a new review.", "Excellent", new Timestamp(98423)
    );

//    AccommodationSearchDTO testAccommodationSearchDTO = new AccommodationSearchDTO(
//            (long)463453243, (long)363543252, "Hotel Park", 4.5, 340, true,
//            "Novi Sad", "Serbia"
//    );

    @PostMapping(consumes="application/json", value = "/login")
    public ResponseEntity<UserDTO> login(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(testUserDTO, HttpStatus.CREATED);
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<UserAccountDTO> createUser(@RequestBody UserAccountDTO registerDTO) {
        return new ResponseEntity<>(testUserAccountDTO, HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(testUserDTO, HttpStatus.OK);
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
        List<UserSummaryDTO> users = new ArrayList<>();
        users.add(testUserSummaryDTO);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getUsersPage(
            Pageable pageable) {
        List<UserSummaryDTO> users = new ArrayList<>();
        users.add(testUserSummaryDTO);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserAccountViewDTO> getUser(
            @PathVariable Long id) {
        return new ResponseEntity<>(testUserAccountViewDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{userId}/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        List<NotificationDTO> notifications = new ArrayList<>();
        notifications.add(testNotificationDTO);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(value="/{userId}/favorites")
    public ResponseEntity<List<AccommodationSearchDTO>> getFavoritesByUserId(@PathVariable Long userId) {
        List<AccommodationSearchDTO> favorites = new ArrayList<>();
        // favorites.add(testAccommodationSearchDTO);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }
 
    @PutMapping(value = "/{userId}/status")
    public ResponseEntity<Void> changeBlockStatus(@PathVariable Long userId,
                                                  @RequestParam boolean isBlocked){
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
