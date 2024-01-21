package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.Amenity;

import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Country;
import jakarta.validation.constraints.Min;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AccommodationWholeDTO {

    protected Long id;
    protected String name;
    protected String description;
    protected String location;
    protected List<String> amenities;
    protected List<Long> images;
    @Min(1)
    protected int minGuests;
    @Min(1)
    protected int maxGuests;
    protected String type;
    protected List<DateRange> availability;
    @Min(0)
    protected double price;

    protected boolean isPricePerGuest;

    protected List<SeasonalRate> seasonalRates;

    protected String city;
    protected String country;
    protected String street;
    @Min(1)
    protected int number;
    @Min(0)
    protected int deadline;

    protected boolean isAutomatic;

    protected String hostUsername;
  

    public AccommodationWholeDTO() {}

    public AccommodationWholeDTO(Accommodation accommodation) {
        this(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), accommodation.getLocation(), Amenity.fromAmenityList(accommodation.getAmenities()), accommodation.getImages(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getType().getValue(), accommodation.getAvailability(), accommodation.getPrice(), accommodation.getIsPricePerGuest(), accommodation.getSeasonalRates(),
                accommodation.getAddress().getCity(), accommodation.getAddress().getCountry().getCountryName(), accommodation.getAddress().getStreet(), accommodation.getAddress().getNumber(), accommodation.getDeadline(), accommodation.getIsAutomatic(), accommodation.getHost().getUsername());
    }

    public AccommodationWholeDTO(PendingAccommodation accommodation) {
        this(accommodation.getAccommodationId(), accommodation.getName(), accommodation.getDescription(), accommodation.getLocation(), Amenity.fromAmenityList(accommodation.getAmenities()), accommodation.getImages(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getType().getValue(), accommodation.getAvailability(), accommodation.getPrice(), accommodation.isPricePerGuest(), accommodation.getSeasonalRates(),
                accommodation.getAddress().getCity(), accommodation.getAddress().getCountry().getCountryName(), accommodation.getAddress().getStreet(), accommodation.getAddress().getNumber(), accommodation.getDeadline(), accommodation.getIsAutomatic(), accommodation.getHost().getUsername());
    }

    public AccommodationWholeDTO(Long id, String name, String description, String location, List<String> amenities, Set<Image> images, int minGuests, int maxGuests, String accommodationType, List<DateRange> availability, double price, boolean isPricePerGuest, List<SeasonalRate> seasonalRates, String city, String country, String street, int number, int deadline, boolean isAutomatic, String hostUsername) {

        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.images = new ArrayList<Long>();
        if (images != null)
            for (Image image : images){
                this.images.add(image.getId());
        }
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.type = accommodationType;
        this.availability = availability;
        this.price = price;
        this.isPricePerGuest = isPricePerGuest;
        this.seasonalRates = seasonalRates;
        this.city = city;
        this.country = country;
        this.street = street;
        this.number = number;
        this.deadline = deadline;
        this.isAutomatic = isAutomatic;
        this.hostUsername = hostUsername;
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

    public List<String> getAmenities() {
        return amenities;
    }

    public void setAmenities(List<String> amenities) {
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

    public boolean getIsPricePerGuest() { return isPricePerGuest; }
    public void setIsPricePerGuest(boolean isPricePerGuest) { this.isPricePerGuest = isPricePerGuest; }
    public List<SeasonalRate> getSeasonalRates() {
        return seasonalRates;
    }

    public void setSeasonalRates(List<SeasonalRate> seasonalRates) {
        this.seasonalRates = seasonalRates;
    }

    public boolean isPricePerNight() {
        return isPricePerGuest;
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

    public boolean getIsAutomatic() {
        return this.isAutomatic;
    }

    public void setIsAutomatic(boolean isAutomatic) {
        this.isAutomatic = isAutomatic;
    }

    public String getHostUsername() {
        return hostUsername;
    }

    public void setHostUsername(String hostUsername) {
        this.hostUsername = hostUsername;
    }

    @Override
    public String toString() {
        return "AccommodationWholeDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", description='" + description + '\'' +
                ", location='" + location + '\'' +
                ", amenities=" + amenities +
                ", images=" + images +
                ", minGuests=" + minGuests +
                ", maxGuests=" + maxGuests +
                ", type='" + type + '\'' +
                ", availability=" + availability +
                ", price=" + price +
                ", isPricePerGuest=" + isPricePerGuest +
                ", seasonalRates=" + seasonalRates +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", deadline=" + deadline +
                ", isAutomatic=" + isAutomatic +
                ", hostUsername='" + hostUsername + '\'' +
                '}';
    }
}

