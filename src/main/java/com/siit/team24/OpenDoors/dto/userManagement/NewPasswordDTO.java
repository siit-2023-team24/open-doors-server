package com.siit.team24.OpenDoors.dto.userManagement;

public class NewPasswordDTO {
    private String username;
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getRepeatPassword() {
        return repeatPassword;
    }

    public void setRepeatPassword(String repeatPassword) {
        this.repeatPassword = repeatPassword;
    }

    public NewPasswordDTO(String email, String oldPassword, String newPassword, String repeatPassword) {
        this.username = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDTO{" +
                "user=" + username +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }


}
