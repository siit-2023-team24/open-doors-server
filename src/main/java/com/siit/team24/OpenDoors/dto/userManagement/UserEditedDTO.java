package com.siit.team24.OpenDoors.dto.userManagement;

import org.springframework.web.multipart.MultipartFile;

public class UserEditedDTO extends UserDTO {
    private MultipartFile file;

    public UserEditedDTO() {
    }

    public UserEditedDTO(Long id, String firstName, String lastName, String phone, String street, int number,
                         String city, String country, Long imageId, MultipartFile file) {
        super(id, firstName, lastName, phone, street, number, city, country, imageId);
        this.file = file;
    }


    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

}
