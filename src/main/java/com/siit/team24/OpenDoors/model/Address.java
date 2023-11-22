package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

@Embeddable
public class Address {
    private String street;
    private int number;
    private String city;
    //TODO: add country
    public String getStreet() {
        return street;
    }

    public int getNumber() {
        return number;
    }

    public String getCity() {
        return city;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
