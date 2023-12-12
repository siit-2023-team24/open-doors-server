package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;

public class UserAccountDTO extends UserDTO {
    private String email;
    private String password;
    private String role;


    public UserAccountDTO() {

    }

    public UserAccountDTO(Long id, String firstName, String lastName, String phone, String street, int number, String city, String country, Long imageId, String email, String password, String role) {
        super(id, firstName, lastName, phone, street, number, city, country, imageId);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserAccountDTO(User user, String email, String password, String role) {
        super(user);
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public UserAccountDTO(String email, String password, String role) {
        this.email = email;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "UserAccountDTO{" +
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
