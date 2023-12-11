package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.userManagement.*;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.service.user.AccountService;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    private AccountService accountService;



    UserDTO testUserDTO = new UserDTO(
            (long)1, "Steve", "Stevens", "2142365516", "Pennsylvania Avenue", 1,
            "Washington", "United States", (long)1
    );

    AccountDTO testAccountDTO = new AccountDTO(
            "steve@testnmail.me", "St3v3St3v3ns"
    );

    UserAccountDTO testUserAccountDTO = new UserAccountDTO(
            (long)1, "Steve", "Stevens", "2142365516", "Pennsylvania Avenue", 1,
            "Washington", "United States", (long)1, "steve@testnmail.me", "St3v3St3v3ns", "guest"
    );

    UserSummaryDTO testUserSummaryDTO = new UserSummaryDTO(
            "bob@testmail.me", "Bob", "Roberts", "host"
    );

    UserAccountViewDTO testUserAccountViewDTO = new UserAccountViewDTO(
            (long)1, "Steve", "Stevens", "2142365516", "Pennsylvania Avenue", 1,
            "Washington", "United States", (long)1, "steve@testnmail.me", "guest"
    );

    NotificationDTO testNotificationDTO = new NotificationDTO(
            "You have a new review.", "Excellent", new Timestamp(98423)
    );

    AccommodationSearchDTO testAccommodationSearchDTO = new AccommodationSearchDTO(
            (long)463453243, (long)363543252, "Hotel Park", 4.5, 340, true,
            "Novi Sad", "Serbia"
    );



    @PostMapping(consumes="application/json", value = "/login")
    public ResponseEntity<UserDTO> login(@RequestBody AccountDTO accountDTO) {
        //todo
        return new ResponseEntity<>(testUserDTO, HttpStatus.CREATED);
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<UserAccountDTO> createUser(@RequestBody UserAccountDTO registerDTO) {
        //todo
        return new ResponseEntity<>(testUserAccountDTO, HttpStatus.CREATED);
    }

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

    @PutMapping(consumes = "application/json", value = "/new-password")
    public ResponseEntity<Void> updateAccount(@RequestBody NewPasswordDTO newPasswordDTO){
        try {
            this.accountService.changePassword(newPasswordDTO);
        } catch (Exception e) {
            System.err.println("Error changing password for: " + newPasswordDTO.getEmail());
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        //TODO test
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {
        //todo
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping(value = "/all")
    public ResponseEntity<List<UserSummaryDTO>> getAllUsers() {
        //todo
        List<UserSummaryDTO> users = new ArrayList<>();
        users.add(testUserSummaryDTO);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<List<UserSummaryDTO>> getUsersPage(
            Pageable pageable) {
        //todo
        List<UserSummaryDTO> users = new ArrayList<>();
        users.add(testUserSummaryDTO);
        return new ResponseEntity<>(users, HttpStatus.OK);
    }


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


    @GetMapping(value = "/{userId}/notifications")
    public ResponseEntity<List<NotificationDTO>> getNotificationsByUserId(@PathVariable Long userId) {
        //todo
        List<NotificationDTO> notifications = new ArrayList<>();
        notifications.add(testNotificationDTO);
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }

    @GetMapping(value="/{userId}/favorites")
    public ResponseEntity<List<AccommodationSearchDTO>> getFavoritesByUserId(@PathVariable Long userId) {
        //todo
        List<AccommodationSearchDTO> favorites = new ArrayList<>();
        favorites.add(testAccommodationSearchDTO);
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }
 
    @PutMapping(value = "/{userId}/status")
    public ResponseEntity<Void> changeBlockStatus(@PathVariable Long userId,
                                                  @RequestParam boolean isBlocked){
        //todo
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
