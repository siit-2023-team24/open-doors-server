package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.Host;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeEditedDTO;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.service.ImageService;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
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


    @PreAuthorize("hasRole('HOST') or hasRole('ADMIN')")
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

    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<Collection<PendingAccommodationHostDTO>> getAllPending() {
        Collection<PendingAccommodationHostDTO> accommodations = pendingService.getAll();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @GetMapping(value = "/host/{hostId}")
    public ResponseEntity<Collection<PendingAccommodationHostDTO>> getPendingForHost(@PathVariable Long hostId) {
        Collection<PendingAccommodationHostDTO> accommodations = pendingService.getForHost(hostId);
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deletePending(@PathVariable Long id) {
        PendingAccommodation pending = pendingService.findById(id);
        pendingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping(value = "deny/{id}")
    public ResponseEntity<Void> denyPending(@PathVariable Long id) {
        PendingAccommodation pending = pendingService.findById(id);
        pendingService.delete(id);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @PreAuthorize("hasRole('HOST')")
    @PostMapping
    public ResponseEntity<PendingAccommodationWholeDTO> save(@Valid @RequestBody PendingAccommodationWholeEditedDTO dto) {
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

    @PreAuthorize("hasRole('HOST')")
    @PostMapping(value = "/{id}/images", consumes = "multipart/form-data")
    public ResponseEntity<PendingAccommodationWholeDTO> saveImages(@PathVariable Long id,
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

    @PreAuthorize("hasRole('ADMIN') or hasRole('HOST')")
    @PutMapping(consumes = "application/json")
    public ResponseEntity<Void> approve(@RequestBody PendingAccommodationHostDTO dto) throws IOException {
        pendingService.approve(dto);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
