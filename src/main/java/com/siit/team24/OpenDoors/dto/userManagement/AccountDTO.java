package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.Account;

public class AccountDTO {
    private String email;
    private String password;
    public AccountDTO(Account account){
        this.email=account.getEmail();
        this.password=account.getPassword();
    }

    public AccountDTO(){

    }

    public AccountDTO(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getEmail() {
        return email;
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
                "email='" + email + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
