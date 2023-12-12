package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.repository.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ImageService {

    @Autowired
    private ImageRepository imageRepository;

    public Image findOne(Long id) {
        return imageRepository.findOneById(id);
    }
}
