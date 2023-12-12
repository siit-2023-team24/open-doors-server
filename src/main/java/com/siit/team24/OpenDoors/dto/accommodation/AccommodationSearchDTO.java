package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Image;

public class AccommodationSearchDTO {
    private Long id;
    private Long image;
    private String name;
    private double averageRating;
    private double price;
    private boolean isPricePerNight;
    private Double totalPrice;
    private String city;
    private String country;

    public AccommodationSearchDTO() {}

    public AccommodationSearchDTO(Accommodation accommodation, Double totalPrice) {
        this(accommodation.getId(), ((Image)accommodation.getImages().toArray()[0]).getId(), accommodation.getName(), accommodation.getAverageRating(), accommodation.getPrice(), accommodation.isPricePerNight(), totalPrice, accommodation.getAddress().getCity(), accommodation.getAddress().getCountry().getCountryName());
    }

    public AccommodationSearchDTO(Long id, Long image, String name, double averageRating, double price, boolean isPricePerNight, Double totalPrice, String city, String country) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.averageRating = averageRating;
        this.price = price;
        this.isPricePerNight = isPricePerNight;
        this.totalPrice = totalPrice;
        this.city = city;
        this.country = country;
    }

    public AccommodationSearchDTO(Accommodation accommodation) {
        this(accommodation.getId(), null, accommodation.getName(), accommodation.getAverageRating(), accommodation.getPrice(), accommodation.isPricePerNight(), 0.0, null, null);
    }

    public Long getId() {
        return id;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(double averageRating) {
        this.averageRating = averageRating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean isPricePerNight() {
        return isPricePerNight;
    }

    public void setPricePerNight(boolean pricePerNight) {
        isPricePerNight = pricePerNight;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
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
}

