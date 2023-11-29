package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;

public class UserAccountViewDTO extends UserDTO {
    private String email;


    public UserAccountViewDTO() {

    }

    public UserAccountViewDTO(Long id, String firstName, String lastName, String phone, String street, int number, String city, String country, Long imageId, String email) {
        super(id, firstName, lastName, phone, street, number, city, country, imageId);
        this.email = email;
    }

    public UserAccountViewDTO(User user, String email) {
        super(user);
        this.email = email;
    }

    public UserAccountViewDTO(String email, String password, String role) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public String toString() {
        return "UserAccountDTO{" +
                "email='" + email + '\'' +
                '}';
    }
}
