package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AccommodationWholeDTO {

    protected Long id;
    protected String name;
    protected String description;
    protected String location;
    protected List<Amenity> amenities;
    protected List<Long> images;
    protected int minGuests;
    protected int maxGuests;
    protected String type;
    protected List<DateRange> availability;
    protected double price;

    protected List<Price> seasonalRates;

    protected String city;
    protected String country;
    protected String street;
    protected int number;
    protected int deadline;

    protected boolean isAutomatic;

    public AccommodationWholeDTO() {}

    public AccommodationWholeDTO(Accommodation accommodation) {
        this(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), accommodation.getLocation(), accommodation.getAmenities(), accommodation.getImages(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getType().name(), accommodation.getAvailability(), accommodation.getPrice(), accommodation.getSeasonalRates(),
                accommodation.getAddress().getCity(), accommodation.getAddress().getCountry().getCountryName(), accommodation.getAddress().getStreet(), accommodation.getAddress().getNumber(), accommodation.getDeadline(), accommodation.isAutomatic());
    }

    public AccommodationWholeDTO(PendingAccommodation accommodation) {
        this(accommodation.getAccommodationId(), accommodation.getName(), accommodation.getDescription(), accommodation.getLocation(), accommodation.getAmenities(), accommodation.getImages(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getType().name(), accommodation.getAvailability(), accommodation.getPrice(), accommodation.getSeasonalRates(),
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

