package com.siit.team24.OpenDoors.model;

import com.siit.team24.OpenDoors.model.enums.Country;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Enumerated;

@Embeddable
public class Address {
    private String street;
    private int number;
    private String city;
    @Enumerated
    private Country country;

    public Address() {
    }

    public Address(String street, int number, String city, Country country) {
        this.street = street;
        this.number = number;
        this.city = city;
        this.country = country;
    }

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

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    @Override
    public String toString() {
        return "Address{" +
                "street='" + street + '\'' +
                ", number=" + number +
                ", city='" + city + '\'' +
                ", country=" + country +
                '}';
    }

    public void update(String country, String city, String street, int number) {
        this.country = Country.fromString(country);
        this.city = city;
        this.street = street;
        this.number = number;
    }
}
