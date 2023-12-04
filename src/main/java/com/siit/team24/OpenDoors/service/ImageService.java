package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.repository.image.FileRepository;
import com.siit.team24.OpenDoors.repository.image.ImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repo;

    private FileRepository fileRepo = new FileRepository();

    public Optional<Image> findById(Long id) {
        return repo.findById(id);
    }

    public Image save(ImageFileDTO file) throws IOException {
        Image image = fileRepo.save(file);
        return repo.save(image);
    }

    public void delete(Long id) {

    }
}
