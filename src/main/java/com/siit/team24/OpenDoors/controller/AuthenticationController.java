package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.userManagement.AccountDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserAccountDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserTokenState;
import com.siit.team24.OpenDoors.exception.BlockedUserException;
import com.siit.team24.OpenDoors.exception.ResourceConflictException;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.service.user.AccountService;
import com.siit.team24.OpenDoors.service.user.UserService;
import com.siit.team24.OpenDoors.util.TokenUtils;
import jakarta.mail.internet.AddressException;
import jakarta.mail.internet.InternetAddress;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.sql.Timestamp;

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
    public ResponseEntity<UserTokenState> login(@Valid @RequestBody AccountDTO accountDTO, HttpServletResponse response) {
        // AuthenticationException will occur on invalid credentials
        try {

            Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                    accountDTO.getUsername(), accountDTO.getPassword()));
            SecurityContextHolder.getContext().setAuthentication(authentication);

            // Create token for user
            User user = (User) authentication.getPrincipal();

            if (user.isBlocked()) throw new BlockedUserException();

            String jwt = tokenUtils.generateToken(user);
            int expiresIn = tokenUtils.getExpiredIn();

            // Return token for a successful response
            return ResponseEntity.ok(new UserTokenState(jwt, expiresIn, "Success"));

        } catch (BadCredentialsException e) {
            UserTokenState errorToken = new UserTokenState();
            errorToken.setMessage("Wrong username or password.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorToken);
        } catch (DisabledException e) {

            UserTokenState errorToken = new UserTokenState();
            errorToken.setMessage("Your account is not enabled.");
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorToken);
        } catch (BlockedUserException e) {
            UserTokenState errorToken = new UserTokenState();
            errorToken.setMessage("Your account is blocked.");
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(errorToken);
        } catch (Exception e) {
            UserTokenState errorToken = new UserTokenState();
            errorToken.setMessage("Unexpected server error");
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorToken);
        }
    }

    @PostMapping(consumes="application/json", value = "/register")
    public ResponseEntity<UserAccountDTO> register(@Valid @RequestBody UserAccountDTO userAccountDTO) throws UnknownHostException {
        User existUser = this.userService.findByUsername(userAccountDTO.getUsername());

        if (existUser != null) {
            throw new ResourceConflictException(existUser.getId(), "Username already exists");
        }

        User user = this.userService.create(userAccountDTO);

        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        userService.sendActivationEmail(userAccountDTO.getUsername(), "http://" +
                InetAddress.getLocalHost().getHostAddress() + ":4200/activate-account?id=" + user.getId() +"&timestamp=" + timestamp.getTime());

        return new ResponseEntity<>(new UserAccountDTO(user), HttpStatus.CREATED);
    }
    @PostMapping("/activate-user/{id}")
    public ResponseEntity<String> activateUser(@PathVariable("id") Long id) {
        this.userService.activateUser(id);
        return ResponseEntity.status(HttpStatus.OK).body("{\"message\": \"User has been enabled!\"}");
    }
}
