package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AccommodationWholeDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private List<Amenity> amenities;
    private List<Long> images;
    private int minGuests;
    private int maxGuests;
    private String type;
    private List<DateRange> availability;
    private double price;
    private boolean isPricePerNight;
    private Double averageRating;
    private Host host;
    private List<Price> seasonalRates;

    private String city;
    private String country;
    private String street;
    private int number;
    private int deadline;

    private boolean isAutomatic;

    public AccommodationWholeDTO() {}

    public AccommodationWholeDTO(Accommodation accommodation) {
        this(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), accommodation.getLocation(), accommodation.getAmenities(), accommodation.getImages(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getType().name(), accommodation.getAvailability(), accommodation.getPrice(), accommodation.getSeasonalRates(),
                accommodation.getAddress().getCity(), accommodation.getAddress().getCountry().getCountryName(), accommodation.getAddress().getStreet(), accommodation.getAddress().getNumber(), accommodation.getDeadline(), accommodation.isAutomatic());
    }

    public AccommodationWholeDTO(Long id, String name, String description, String location, List<Amenity> amenities, Set<Image> images, int minGuests, int maxGuests, String accommodationType, List<DateRange> availability, double price, List<Price> seasonalRates, String city, String country, String street, int number, int deadline, boolean isAutomatic) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.images = new ArrayList<Long>();
        for (Image image : images){
            this.images.add(image.getId());
        }
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = accommodationType;
        this.availability = availability;
        this.price = price;
        this.isPricePerNight = isPricePerNight;
        this.averageRating = averageRating;
        this.host = host;
        this.seasonalRates = seasonalRates;
        this.city = city;
        this.country = country;
        this.street = street;
        this.number = number;
        this.deadline = deadline;
        this.isAutomatic = isAutomatic;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public List<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<Amenity> amenities) {
        this.amenities = amenities;
    }

    public List<Long> getImages() {
        return images;
    }

    public void setImages(List<Long> images) {
        this.images = images;
    }

    public int getMinGuests() {
        return minGuests;
    }

    public void setMinGuests(int minGuests) {
        this.minGuests = minGuests;
    }

    public int getMaxGuests() {
        return maxGuests;
    }

    public void setMaxGuests(int maxGuests) {
        this.maxGuests = maxGuests;
    }

    public String getType() {
        return type;
    }

    public void setType(String accommodationType) {
        this.type = accommodationType;
    }

    public List<DateRange> getAvailability() {
        return availability;
    }

    public void setAvailability(List<DateRange> availability) {
        this.availability = availability;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public List<Price> getSeasonalRates() {
        return seasonalRates;
    }

    public void setSeasonalRates(List<Price> seasonalRates) {
        this.seasonalRates = seasonalRates;
    }

    public boolean isPricePerNight() {
        return isPricePerNight;
    }

    public void setPricePerNight(boolean pricePerNight) {
        isPricePerNight = pricePerNight;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public Host getHost() {
        return host;
    }

    public void setHost(Host host) {
        this.host = host;
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

    public int getDeadline() {
        return deadline;
    }

    public void setDeadline(int deadline) {
        this.deadline = deadline;
    }

    public boolean isAutomatic() {
        return isAutomatic;
    }

    public void setAutomatic(boolean automatic) {
        isAutomatic = automatic;
    }
}

