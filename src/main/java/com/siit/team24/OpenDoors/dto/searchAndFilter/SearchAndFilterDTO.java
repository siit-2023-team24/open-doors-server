package com.siit.team24.OpenDoors.dto.searchAndFilter;

import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;

import java.time.LocalDate;
import java.util.Set;

public class SearchAndFilterDTO {
    private String location;
    private Integer guestNumber;
    private LocalDate startDate;
    private LocalDate endDate;
    private Double startPrice;
    private Double endPrice;
    private Set<AccommodationType> types;
    private Set<Amenity> amenities;

    public SearchAndFilterDTO() {
    }

    public SearchAndFilterDTO(String location, int guestNumber, LocalDate startDate, LocalDate endDate,
                              double startPrice, double endPrice, Set<AccommodationType> types, Set<Amenity> amenities) {
        this.location = location;
        this.guestNumber = guestNumber;
        this.startDate = startDate;
        this.endDate = endDate;
        this.startPrice = startPrice;
        this.endPrice = endPrice;
        this.types = types;
        this.amenities = amenities;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Integer getGuestNumber() {
        return guestNumber;
    }

    public void setGuestNumber(int guestNumber) {
        this.guestNumber = guestNumber;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(double endPrice) {
        this.endPrice = endPrice;
    }

    public Set<AccommodationType> getTypes() {
        return types;
    }

    public void setTypes(Set<AccommodationType> types) {
        this.types = types;
    }

    public Set<Amenity> getAmenities() {
        return amenities;
    }

    public void setAmenities(Set<Amenity> amenities) {
        this.amenities = amenities;
    }

    @Override
    public String toString() {
        return "SearchAndFilterDTO{" +
                "location='" + location + '\'' +
                ", guestNumber=" + guestNumber +
                ", startDate=" + startDate +
                ", endDate=" + endDate +
                ", startPrice=" + startPrice +
                ", endPrice=" + endPrice +
                ", types=" + types +
                ", amenities=" + amenities +
                '}';
    }
}
