package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.image.ImageBytesDTO;
import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.enums.ImageType;
import com.siit.team24.OpenDoors.repository.image.FileRepository;
import com.siit.team24.OpenDoors.repository.image.ImageRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;
import java.util.Set;

@Service
public class ImageService {

    @Autowired
    private ImageRepository repo;

    private FileRepository fileRepo = new FileRepository();

    public Optional<Image> findById(Long id) {
        return repo.findById(id);
    }

    public byte[] findById(Long id, boolean isProfile) throws IOException {

        Optional<Image> image = repo.findById(id);
        return fileRepo.getFile(image, isProfile);
    }


    public ImageBytesDTO getImageBytesDTO(Long id, boolean isProfile, Long entityId) throws IOException {
        Optional<Image> image = repo.findById(id);
        if (image.isEmpty()) throw new EntityNotFoundException();

        byte[] bytes = findById(id, isProfile);
        ImageType newType;
        if (image.get().getPath().contains("pending-accommodation"))
            newType = ImageType.ACCOMMODATION;
        else newType = ImageType.PENDING_ACCOMMODATION;

        return new ImageBytesDTO(image.get().getName(), bytes, entityId, newType);
    }



    public Image save(ImageFileDTO file) throws IOException {
        Image image = fileRepo.save(file);
        return repo.save(image);
    }


    public Image saveBytes(ImageBytesDTO dto) {
        Image image = fileRepo.saveBytes(dto);
        return repo.save(image);
    }



    public void delete(Long id) {
        Optional<Image> image = repo.findById(id);
        if (image.isEmpty()) {
            System.err.println("Image not found: " + id );
            return;
        }
        repo.deleteById(id);
        fileRepo.delete(image.get());
    }

    public void deleteAll(Set<Image> images) {
        if (images == null) return;
        for (Image image: images) {
            delete(image.getId());
        }
    }
}
