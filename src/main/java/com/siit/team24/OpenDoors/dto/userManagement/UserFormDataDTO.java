package com.siit.team24.OpenDoors.dto.userManagement;

import jakarta.validation.constraints.Min;
import org.springframework.lang.Nullable;
import org.springframework.web.multipart.MultipartFile;

public class UserFormDataDTO {

    private String id;
    private String firstName;
    private String lastName;
    private String phone;
    private String street;
    @Min(1)
    private String number;
    private String city;
    private String country;
    private String imageId;
    @Nullable
    private MultipartFile file;


    public UserFormDataDTO() {
    }

    public UserFormDataDTO(String id, String firstName, String lastName, String phone,
                           String street, String number, String city, String country, String imageId,
                           MultipartFile file) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
        this.imageId = imageId;
        this.file = file;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }

    public MultipartFile getFile() {
        return file;
    }

    public void setFile(MultipartFile file) {
        this.file = file;
    }

    @Override
    public String toString() {
        return "UserDataPackageDTO{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", number='" + number + '\'' +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", imageId='" + imageId + '\'' +
                ", file=" + file +
                '}';
    }

    public UserEditedDTO toEditedDTO() {
        id = id.replaceAll("\"", "");
        firstName = firstName.replaceAll("\"", "");
        lastName = lastName.replaceAll("\"", "");
        phone = phone.replaceAll("\"", "");
        street = street.replaceAll("\"", "");
        number = number.replaceAll("\"", "");
        city = city.replaceAll("\"", "");
        country = country.replaceAll("\"", "");
        Long imgId = null;
        if (imageId != null && !imageId.isEmpty()) {
            imageId = imageId.replaceAll("\"", "");
            imgId = Long.parseLong(imageId);
        }
        return new UserEditedDTO(Long.parseLong(id), firstName, lastName, phone, street, Integer.parseInt(number),
                city, country, imgId, file);
    }
}
