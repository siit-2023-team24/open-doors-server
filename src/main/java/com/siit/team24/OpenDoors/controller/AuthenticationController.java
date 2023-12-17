package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.userManagement.AccountDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserAccountDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserTokenState;
import com.siit.team24.OpenDoors.exception.ResourceConflictException;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.service.user.AccountService;
import com.siit.team24.OpenDoors.service.user.UserService;
import com.siit.team24.OpenDoors.util.TokenUtils;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

@RestController
@RequestMapping("open-doors/auth")
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(consumes="application/json", value = "/login")
    public ResponseEntity<UserTokenState> login(@RequestBody AccountDTO accountDTO, HttpServletResponse response) {
        // Ukoliko kredencijali nisu ispravni, logovanje nece biti uspesno, desice se
        // AuthenticationException
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                accountDTO.getUsername(), accountDTO.getPassword()));

        // Ukoliko je autentifikacija uspesna, ubaci korisnika u trenutni security
        // kontekst
        SecurityContextHolder.getContext().setAuthentication(authentication);

        // Kreiraj token za tog korisnika
        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        // Vrati token kao odgovor na uspesnu autentifikaciju
        return ResponseEntity.ok(new UserTokenState(jwt, expiresIn));
    }

    @PostMapping(consumes="application/json")
    public ResponseEntity<User> createUser(@RequestBody UserAccountDTO userAccountDTO, UriComponentsBuilder ucBuilder) {
        User existUser = this.userService.findByUsername(userAccountDTO.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(existUser.getId(), "Username already exists");
        }

        User user = this.userService.save(userAccountDTO);

        return new ResponseEntity<>(user, HttpStatus.CREATED);
    }
}
