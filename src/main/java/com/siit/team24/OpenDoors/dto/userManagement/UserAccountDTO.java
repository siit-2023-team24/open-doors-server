package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class UserAccountDTO extends UserDTO {
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String username;
    @Length(min = 5)
    private String password;
    private String role;


    public UserAccountDTO() {

    }

    public UserAccountDTO(Long id, String firstName, String lastName, String phone, String street, int number, String city, String country, Long imageId, String email, String password, String role) {
        super(id, firstName, lastName, phone, street, number, city, country, imageId);
        this.username = email;
        this.password = password;
        this.role = role;
    }

    public UserAccountDTO(User user) {
        this(user.getId(), user.getFirstName(), user.getLastName(), user.getPhone(),
                user.getAddress().getStreet(), user.getAddress().getNumber(),
                user.getAddress().getCity(), user.getAddress().getCountry().getCountryName(),
                null, user.getUsername(), user.getPassword(), user.getRole().name());
        if(user.getImage() != null) this.imageId = user.getImage().getId();
    }

    public UserAccountDTO(User user, String email, String password, String role) {
        super(user);
        this.username = email;
        this.password = password;
        this.role = role;
    }

    public UserAccountDTO(String email, String password, String role) {
        this.username = email;
        this.password = password;
        this.role = role;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
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
                "email='" + username + '\'' +
                ", password='" + password + '\'' +
                ", role='" + role + '\'' +
                '}';
    }
}
