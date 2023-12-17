package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
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
    private PendingAccommodationService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<PendingAccommodationWholeDTO> getById(@PathVariable Long id) {
        try {
            PendingAccommodationWholeDTO dto = new PendingAccommodationWholeDTO(service.findById(id));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Pending accommodation not found with id: " + id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/{id}/editing")
    public ResponseEntity<PendingAccommodationWholeDTO> getPendingById(@PathVariable Long id) {
        try {
            PendingAccommodationWholeDTO dto = new PendingAccommodationWholeDTO(service.findById(id));
            return new ResponseEntity<>(dto, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            System.err.println("Pending accommodation not found with id: " + id);
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<Collection<PendingAccommodationHostDTO>> getPendingForHost(@PathVariable Long hostId) {
        Collection<PendingAccommodationHostDTO> accommodations = service.getForHost(hostId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

//    @PutMapping
//    public ResponseEntity<Void> updatePending(@RequestBody PendingAccommodationWholeDTO dto) {
//        //TODO
//        return new ResponseEntity<>(HttpStatus.OK);
//    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePending(@PathVariable Long id) {
        service.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //create new or edit existing accommodation - active or pending
    @PostMapping(consumes = "application/json")
    public ResponseEntity<PendingAccommodationWholeDTO> save(@RequestBody PendingAccommodationWholeDTO dto) {
        PendingAccommodation pendingAccommodation = service.save(dto);
        System.out.println("New: " + pendingAccommodation);
        return new ResponseEntity<>(new PendingAccommodationWholeDTO(pendingAccommodation), HttpStatus.CREATED);
    }
}
