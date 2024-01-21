package com.siit.team24.OpenDoors.dto.userManagement;

import com.siit.team24.OpenDoors.model.User;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;
import org.hibernate.validator.constraints.Length;

public class UserDTO {
    protected Long id;
    protected String firstName;
    protected String lastName;
    @Pattern(regexp = "\\d{10}")
    protected String phone;
    protected String street;
    @Min(1)
    protected int number;
    protected String city;
    protected String country;
    protected Long imageId;

    public UserDTO(Long id, String firstName, String lastName, String phone, String street, int number, String city, String country, Long imageId) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.phone = phone;
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
        this.imageId = imageId;
    }

    public UserDTO(User user){
        this.id = user.getId();
        this.firstName = user.getFirstName();
        this.lastName = user.getLastName();
        this.phone = user.getPhone();
        this.street = user.getAddress().getStreet();
        this.number = user.getAddress().getNumber();
        this.city = user.getAddress().getCity();
        this.country = user.getAddress().getCountry().getCountryName();
        this.imageId = user.getImage().getId();
    }

    public UserDTO() {

    }

    public Long getId() {
        return id;
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

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
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

    public Long getImageId() {
        return imageId;
    }

    public void setImageId(Long image) {
        this.imageId = image;
    }

    @Override
    public String toString() {
        return "UserDTO{" +
                "firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", phone='" + phone + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", imageId='" + imageId + '\'' +
                '}';
    }
}
