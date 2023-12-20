package com.siit.team24.OpenDoors.dto.pendingAccommodation;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.model.SeasonalRate;
import com.siit.team24.OpenDoors.model.enums.Amenity;

import java.util.List;
import java.util.Set;

public class PendingAccommodationWholeDTO extends AccommodationWholeDTO {
    protected Long accommodationId;

    public PendingAccommodationWholeDTO() {
    }

    public PendingAccommodationWholeDTO(PendingAccommodation accommodation) {
        super(accommodation);
        this.id = accommodation.getId();
        this.accommodationId = accommodation.getAccommodationId();
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    @Override
    public String toString() {
        return "PendingAccommodationWholeDTO{" +
                "accommodationId=" + accommodationId +
                ", id=" + id +
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
                ", seasonalRates=" + seasonalRates +
                ", city='" + city + '\'' +
                ", country='" + country + '\'' +
                ", street='" + street + '\'' +
                ", number=" + number +
                ", deadline=" + deadline +
                ", isAutomatic=" + isAutomatic +
                '}';
    }
}
