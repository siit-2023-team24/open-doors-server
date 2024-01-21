package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Image;

import java.util.Set;
public class AccommodationSearchDTO {
    private Long id;
    private Long image;
    private String name;
    private Double averageRating;
    private double price;
    private boolean isPricePerGuest;
    private Double totalPrice;
    private String city;
    private String country;
    private boolean isFavoriteForGuest;

    public AccommodationSearchDTO() {}

    public AccommodationSearchDTO(Accommodation accommodation, Double totalPrice) {
        this(accommodation.getId(), ((Image)accommodation.getImages().toArray()[0]).getId(), accommodation.getName(), null, accommodation.getPrice(), accommodation.getIsPricePerGuest(), totalPrice, accommodation.getAddress().getCity(), accommodation.getAddress().getCountry().getCountryName());

    }

    public AccommodationSearchDTO(Long id, Long image, String name, Double averageRating, double price, boolean isPricePerGuest, Double totalPrice, String city, String country) {
        this.id = id;
        this.image = image;
        this.name = name;
        this.averageRating = averageRating;
        this.price = price;
        this.isPricePerGuest = isPricePerGuest;
        this.totalPrice = totalPrice;
        this.city = city;
        this.country = country;
        this.isFavoriteForGuest = false;
    }

    public AccommodationSearchDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.image = getFirstImageId(accommodation);
        this.name = accommodation.getName();
        this.averageRating = null;
        this.price = accommodation.getPrice();
        this.isPricePerGuest = accommodation.getIsPricePerGuest();
        this.totalPrice = 0.0;
        this.city = accommodation.getAddress().getCity();
        this.country = accommodation.getAddress().getCountry().getCountryName();
    }

    private Long getFirstImageId(Accommodation accommodation) {
        Set<Image> images = accommodation.getImages();
        Image firstImage = images.isEmpty() ? null : images.iterator().next();
        return (firstImage != null) ? firstImage.getId() : null;
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

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public boolean getIsPricePerGuest() {
        return isPricePerGuest;
    }

    public void setIsPricePerGuest(boolean pricePerGuest) {
        isPricePerGuest = pricePerGuest;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
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

    public boolean getIsFavoriteForGuest() {
        return isFavoriteForGuest;
    }

    public void setIsFavoriteForGuest(boolean isFavorite) {
        this.isFavoriteForGuest = isFavorite;
    }
    @Override
    public String toString() {
        return "AccommodationSearchDTO{" +
                "id=" + id +
                ", image=" + image +
                ", name='" + name + '\'' +
                ", averageRating=" + averageRating +
                ", price=" + price +
                ", isPricePerGuest=" + isPricePerGuest +
                ", totalPrice=" + totalPrice +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", isFavorite=" + isFavoriteForGuest + '\'' +
                '}';
    }
}

