package com.siit.team24.OpenDoors.dto.userManagement;

public class NewPasswordDTO {
    private String email;
    private String oldPassword;
    private String newPassword;
    private String repeatPassword;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPasswod() {
        return oldPassword;
    }

    public void setOldPasswod(String oldPassword) {
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
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.repeatPassword = repeatPassword;
    }

    @Override
    public String toString() {
        return "NewPasswordDTO{" +
                "user=" + email +
                ", oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }


}
