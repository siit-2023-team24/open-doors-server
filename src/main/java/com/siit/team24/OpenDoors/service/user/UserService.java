package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.dto.image.ImageFileDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserEditedDTO;
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

        //image changed?
        if (newData.getFile() != null) {
            if (user.getImage() != null) {
                imageService.delete(user.getImage().getId());
            }
            imageService.save(new ImageFileDTO(newData.getImageId(), newData.getFile(), true, user.getId()));
        }
        user.updateSimpleValues(newData); //updates all attributes except for image
        User updated = repo.save(user);
        return updated.toEditedDTO();
    }
}
