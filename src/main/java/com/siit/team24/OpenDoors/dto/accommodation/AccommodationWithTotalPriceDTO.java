package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.*;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Country;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

public class AccommodationWithTotalPriceDTO {
    private Long id;
    private String name;
    private String description;
    private String location;
    private List<String> amenities;
    private List<Long> images;
    private int minGuests;
    private int maxGuests;
    private AccommodationType accommodationType;
    private List<DateRange> availability;
    private double price;
    private boolean isPricePerGuest;
    private Double totalPrice;
    private Double averageRating;
    private String host;
    private List<SeasonalRate> seasonalRates;
    private String country;
    private String city;
    private String street;
    private int number;
    private boolean isFavoriteForGuest;
    private Long hostId;
    private boolean blocked;

    public AccommodationWithTotalPriceDTO() {}

    public AccommodationWithTotalPriceDTO(Accommodation accommodation, Double totalPrice) {
        this(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), accommodation.getLocation(), accommodation.getAmenities(), accommodation.getImages(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getType(), accommodation.getAvailability(), accommodation.getPrice(), accommodation.getSeasonalRates(),
                accommodation.getAddress().getCity(), accommodation.getAddress().getCountry().getCountryName(), accommodation.getAddress().getStreet(), accommodation.getAddress().getNumber(), accommodation.getIsPricePerGuest(), totalPrice, null, accommodation.getHost().getUsername(), accommodation.getHost().getId(), accommodation.isBlocked());
    }

    public AccommodationWithTotalPriceDTO(Long id, String name, String description, String location, List<Amenity> amenities, Set<Image> images, int minGuests, int maxGuests, AccommodationType accommodationType, List<DateRange> availability, double price, List<SeasonalRate> seasonalRates, String city, String country, String street, int number, boolean isPricePerNight, Double totalPrice, Double averageRating, String host, Long hostId, boolean blocked) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = Amenity.fromAmenityList(amenities);
        this.totalPrice = totalPrice;
        this.images = new ArrayList<Long>();
        for (Image image : images){
            this.images.add(image.getId());
        }
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
        this.accommodationType = accommodationType;
        this.availability = availability;
        this.price = price;
        this.isPricePerGuest = isPricePerNight;
        this.averageRating = averageRating;
        this.host = host;
        this.seasonalRates = seasonalRates;
        this.city = city;
        this.country = country;
        this.street = street;
        this.number = number;
        this.isFavoriteForGuest = false;
        this.hostId = hostId;
        this.blocked = blocked;
    }

    public Long getHostId() {
        return hostId;
    }

    public void setHostId(Long hostId) {
        this.hostId = hostId;
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

    public AccommodationType getAccommodationType() {
        return accommodationType;
    }

    public void setAccommodationType(AccommodationType accommodationType) {
        this.accommodationType = accommodationType;
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

    public List<SeasonalRate> getSeasonalRates() {
        return seasonalRates;
    }

    public void setSeasonalRates(List<SeasonalRate> seasonalRates) {
        this.seasonalRates = seasonalRates;
    }

    public boolean getIsPricePerGuest() {
        return isPricePerGuest;
    }

    public void setIsPricePerGuest(boolean pricePerGuest) {
        isPricePerGuest = pricePerGuest;
    }

    public Double getAverageRating() {
        return averageRating;
    }

    public void setAverageRating(Double averageRating) {
        this.averageRating = averageRating;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
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

    public boolean getIsFavoriteForGuest() {
        return isFavoriteForGuest;
    }

    public void setIsFavoriteForGuest(boolean isFavoriteForGuest) {
        this.isFavoriteForGuest = isFavoriteForGuest;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }
}

