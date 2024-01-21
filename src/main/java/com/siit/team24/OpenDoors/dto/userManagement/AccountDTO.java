package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;
import jakarta.validation.constraints.Email;
import org.hibernate.validator.constraints.Length;

public class AccountDTO {
    @Email(regexp = "^[\\w-\\.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
    private String username;
    @Length(min = 5)
    private String password;
    public AccountDTO(User account){
        this.username =account.getUsername();
        this.password=account.getPassword();
    }

    public AccountDTO() {

    }

    public AccountDTO(String email, String password) {
        this.username = email;
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "AccountDTO{" +
                "email='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
