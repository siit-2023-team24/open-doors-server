package com.siit.team24.OpenDoors.dto;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Image;

public class AccommodationSearchDTO {
    private Image image;
    private String name;
    private double averageRating;
    private double price;
    private boolean isPricePerNight;

    public AccommodationSearchDTO() {}

    public AccommodationSearchDTO(Accommodation accommodation) {
        this((Image) accommodation.getImages().toArray()[0], accommodation.getName(), accommodation.getAverageRating(), accommodation.getPrice(), accommodation.isPricePerNight());
    }

    public AccommodationSearchDTO(Image image, String name, double averageRating, double price, boolean isPricePerNight) {
        this.image = image;
        this.name = name;
        this.averageRating = averageRating;
        this.price = price;
        this.isPricePerNight = isPricePerNight;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
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
}
