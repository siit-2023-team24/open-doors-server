package com.siit.team24.OpenDoors.dto.searchAndFilter;

import com.siit.team24.OpenDoors.model.enums.AccommodationType;
import com.siit.team24.OpenDoors.model.enums.Amenity;
import com.siit.team24.OpenDoors.validation.SearchAndFilterEndDateAfterStartDate;
import com.siit.team24.OpenDoors.validation.SearchAndFilterEndPriceGreaterThanStartPrice;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.Pattern;

import java.sql.Timestamp;
import java.util.Set;
@SearchAndFilterEndDateAfterStartDate
@SearchAndFilterEndPriceGreaterThanStartPrice
public class SearchAndFilterDTO {
//    @Pattern(regexp = "^[-+]?([1-8]?\\d(\\.\\d+)?|90(\\.0+)?)\\s+[-+]?(180(\\.0+)?|((1[0-7]\\d)|([1-9]?\\d))(\\.\\d+)?)$")
    private String location;
    @Min(value = 1)
    private Integer guestNumber;
    @FutureOrPresent
    private Timestamp startDate;
    @FutureOrPresent
    private Timestamp endDate;

    @Min(value = 0)
    private Double startPrice;
    @Min(value = 0)
    private Double endPrice;
    private Set<AccommodationType> types;
    private Set<Amenity> amenities;

    public SearchAndFilterDTO() {
    }

    public SearchAndFilterDTO(String location, int guestNumber, Timestamp startDate, Timestamp endDate,
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

    public void setGuestNumber(Integer guestNumber) {
        this.guestNumber = guestNumber;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Double getStartPrice() {
        return startPrice;
    }

    public void setStartPrice(Double startPrice) {
        this.startPrice = startPrice;
    }

    public Double getEndPrice() {
        return endPrice;
    }

    public void setEndPrice(Double endPrice) {
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
                "location=" + location +
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
