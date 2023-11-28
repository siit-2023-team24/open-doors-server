package com.siit.team24.OpenDoors.controller;

import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.InputStream;


@RestController
@RequestMapping(value = "open-doors/image")
public class ImageController {

    @GetMapping(value = "/{id}")
    public ResponseEntity<InputStreamResource> getImage(@PathVariable Long id,
                                                        @RequestParam("jpg") boolean jpg) {
        MediaType contentType = jpg ? MediaType.IMAGE_JPEG : MediaType.IMAGE_PNG;
        //temporarily
        InputStream in = getClass().getResourceAsStream("/static/logo.png");
        return ResponseEntity.ok().contentType(contentType).body(new InputStreamResource(in));
    }

    //TODO post, delete
}
