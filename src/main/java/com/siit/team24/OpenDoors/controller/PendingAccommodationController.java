package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.Host;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;

@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/pending-accommodations")
public class PendingAccommodationController {

    @Autowired
    private PendingAccommodationService pendingService;

    @Autowired
    private UserService userService;


    @GetMapping(value = "/{id}")
    public ResponseEntity<PendingAccommodationWholeDTO> getById(@PathVariable Long id) {
        try {
            PendingAccommodationWholeDTO dto = new PendingAccommodationWholeDTO(pendingService.findById(id));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Pending accommodation not found with id: " + id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Collection<PendingAccommodationHostDTO>> getAllPending() {
        Collection<PendingAccommodationHostDTO> accommodations = pendingService.getAll();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<Collection<PendingAccommodationHostDTO>> getPendingForHost(@PathVariable Long hostId) {
        Collection<PendingAccommodationHostDTO> accommodations = pendingService.getForHost(hostId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('HOST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePending(@PathVariable Long id) {
        pendingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    // @PreAuthorize("hasRole('HOST')")
    //create new or edit existing accommodation - active or pending
    @PostMapping(consumes = "application/json")
    public ResponseEntity<PendingAccommodationWholeDTO> save(@RequestBody PendingAccommodationWholeDTO dto) {
        System.out.println("Received: " + dto);

        PendingAccommodation pendingAccommodation = new PendingAccommodation();
        pendingAccommodation.setSimpleValues(dto);  //everything except for images, host

        Host host = (Host)userService.findByUsername(dto.getHostUsername());
        pendingAccommodation.setHost(host);

        //TODO images
        PendingAccommodation saved = pendingService.save(pendingAccommodation);
        System.out.println("New: " + saved);
        return new ResponseEntity<>(new PendingAccommodationWholeDTO(saved), HttpStatus.CREATED);
    }

    @PutMapping(consumes = "application/json")
    public ResponseEntity<Void> approve(@RequestBody PendingAccommodationHostDTO dto) {
        pendingService.approve(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
