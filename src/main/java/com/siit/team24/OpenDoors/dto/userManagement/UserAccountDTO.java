package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.Account;
import com.siit.team24.OpenDoors.model.User;

public class UserAccountDTO {
    private AccountDTO account;
//    private UserDTO user;
    private String role;
    public UserAccountDTO(User user){
        this.account = new AccountDTO(user.getAccount());
        this.role = user.getAccount().getRole().name();
//        this.user = new UserDTO(user);
    }

    public UserAccountDTO(Account account){
        this.account = new AccountDTO(account);
        this.role = account.getRole().name();
//        this.user = new UserDTO(account.getUser());
    }

    public UserAccountDTO() {

    }

    public AccountDTO getAccount() {
        return account;
    }

    public void setAccount(AccountDTO account) {
        this.account = account;
    }

//    public UserDTO getUser() {
//        return user;
//    }

//    public void setUser(UserDTO user) {
//        this.user = user;
//    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String toString() {
        return "RegisterDTO{" +
                "account=" + account +
//                ", user=" + user +
                ", role='" + role + '\'' +
                '}';
    }
}
