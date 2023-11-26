package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.userManagement.AccountDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserAccountDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserSummaryDTO;
import com.siit.team24.OpenDoors.model.Account;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.awt.print.Pageable;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api/users")
public class UserController {
    @PostMapping(consumes="application/json", value = "/login")
    public ResponseEntity<AccountDTO> login(@RequestBody AccountDTO accountDTO) {
        return new ResponseEntity<>(new AccountDTO(new Account()),HttpStatus.CREATED);
    }

    @PostMapping(consumes="application/json", value = "/register")
    public ResponseEntity<UserAccountDTO> createUser(@RequestBody UserAccountDTO registerDTO) {
        return new ResponseEntity<>(new UserAccountDTO(new Account()), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json", value = "/updateUser")
    public ResponseEntity<UserDTO> updateUser(@RequestBody UserDTO userDTO){
        return new ResponseEntity<>(new UserDTO(), HttpStatus.OK);
    }
    @PutMapping(consumes = "application/json", value = "/updateAccount")
    public ResponseEntity<AccountDTO> updateAccount(@RequestBody AccountDTO accountDTO){
        return new ResponseEntity<>(new AccountDTO(new Account()),HttpStatus.OK);
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
}
