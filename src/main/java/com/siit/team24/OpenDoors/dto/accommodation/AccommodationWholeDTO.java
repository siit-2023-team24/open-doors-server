package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.Price;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.enums.AccommodationType;

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
    private AccommodationType accommodationType;
    private List<DateRange> availability;
    private double price;

    private List<Price> seasonalRates;

    public AccommodationWholeDTO() {}

    public AccommodationWholeDTO(Accommodation accommodation) {
        this(accommodation.getId(), accommodation.getName(), accommodation.getDescription(), accommodation.getLocation(), accommodation.getAmenities(), accommodation.getImages(), accommodation.getMinGuests(), accommodation.getMaxGuests(), accommodation.getAccommodationType(), accommodation.getAvailability(), accommodation.getPrice(), accommodation.getSeasonalRates());
    }

    public AccommodationWholeDTO(Long id, String name, String description, String location, List<Amenity> amenities, Set<Image> images, int minGuests, int maxGuests, AccommodationType accommodationType, List<DateRange> availability, double price, List<Price> seasonalRates) {
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
        this.accommodationType = accommodationType;
        this.availability = availability;
        this.price = price;
        this.seasonalRates = seasonalRates;
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

    public List<Price> getSeasonalRates() {
        return seasonalRates;
    }

    public void setSeasonalRates(List<Price> seasonalRates) {
        this.seasonalRates = seasonalRates;
    }
}

