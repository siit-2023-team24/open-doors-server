package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeEditedDTO;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Collection;
import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/pending-accommodations")
public class PendingAccommodationController {

    @Autowired
    private PendingAccommodationService pendingService;


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
    @PostMapping
    public ResponseEntity<PendingAccommodationWholeDTO> save(@RequestBody PendingAccommodationWholeEditedDTO dto) {
        System.out.println("Received: " + dto);
        try {
            PendingAccommodation pendingAccommodation = pendingService.save(dto);
            System.out.println("New: " + pendingAccommodation);
            return new ResponseEntity<>(new PendingAccommodationWholeDTO(pendingAccommodation), HttpStatus.CREATED);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }

    @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
    public ResponseEntity<PendingAccommodationWholeDTO> save(@PathVariable Long id,
                                                             @RequestBody List<MultipartFile> images) {
        try {
            if (images == null) {
                System.err.println("images is null");
                return new ResponseEntity<>(null, HttpStatus.OK);
            }
            for (MultipartFile mf: images) {
                System.out.println(mf.getOriginalFilename());
            }
            PendingAccommodation pendingAccommodation = pendingService.saveImages(images, id);
            return new ResponseEntity<>(new PendingAccommodationWholeDTO(pendingAccommodation), HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException(e);
        }
    }
}
