package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserEditedDTO;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.repository.user.UserRepository;
import com.siit.team24.OpenDoors.service.ImageService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository repo;

    @Autowired
    private ImageService imageService;


    public User findById(Long id) {
        Optional<User> user = repo.findById(id);
        if (user.isEmpty()){
            throw new EntityNotFoundException();}
        return user.get();
    }

    public UserEditedDTO update(UserEditedDTO newData) throws IOException {
        User user = this.findById(newData.getId());

        Image oldImage = user.getImage();
        if (oldImage != null && (newData.getImageId() == null || newData.getFile() != null)) { //the user wants to delete or replace their image
            user.setImage(null);
            imageService.delete(oldImage.getId());
        }
        if (newData.getFile() != null) {    //uploaded new image
            Image newImage = imageService.save(new ImageFileDTO(newData.getImageId(), newData.getFile(), true, user.getId()));
            user.setImage(newImage);
        }
        user.updateSimpleValues(newData); //updates all attributes except for image
        User updated = repo.save(user);
        return updated.toEditedDTO();
    }
}
