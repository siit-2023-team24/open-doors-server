package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;


@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/image")
public class ImageController {

    @Autowired
    private ImageService service;

    @GetMapping(value = "/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable Long id) {
        //todo
        InputStream in = getClass().getResourceAsStream("/static/logo.png");
        return ResponseEntity.ok().body(new InputStreamResource(in));
    }

    @PostMapping(value = "/for/{id}/{isProfile}")
    public ResponseEntity<Long> uploadImage(@RequestParam("file") MultipartFile file,
                                            @PathVariable Long id,
                                            @PathVariable boolean isProfile) {
        System.out.println("Received file: " + file.getOriginalFilename());
        try {
            Image image = service.save(new ImageFileDTO(file, isProfile, id));
            return new ResponseEntity<>(image.getId(), HttpStatus.CREATED);
        } catch (IOException e) {
            System.out.println("\nERROR SAVING IMAGE\n");
            e.printStackTrace();
            return new ResponseEntity<>(-1L, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteImage(@PathVariable Long id) {
        //todo
        return new ResponseEntity<>(HttpStatus.OK);
    }

}
