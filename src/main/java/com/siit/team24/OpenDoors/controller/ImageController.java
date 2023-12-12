package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;


@CrossOrigin
@RestController
@RequestMapping(value = "open-doors/image")
public class ImageController {

    @Autowired
    private ImageService service;

    @GetMapping(value = "/{id}/profile/{isProfile}")
    public ResponseEntity<ByteArrayResource> getImage(@PathVariable Long id, @PathVariable boolean isProfile) {
        try {
            byte[] bytes = service.findById(id, isProfile);
            System.out.println("Sent image");
            return ResponseEntity.ok().body(new ByteArrayResource(bytes));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.internalServerError().body(null);
        }
    }
}
