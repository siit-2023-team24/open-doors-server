package com.siit.team24.OpenDoors.dto.pendingAccommodation;

import com.siit.team24.OpenDoors.model.PendingAccommodation;

import java.util.List;

public class PendingAccommodationWholeEditedDTO extends PendingAccommodationWholeDTO {

    private List<Long> toDeleteImages;

    public PendingAccommodationWholeEditedDTO() {
    }

    public PendingAccommodationWholeEditedDTO(PendingAccommodation accommodation, List<Long> toDeleteImages) {
        super(accommodation);
        this.toDeleteImages = toDeleteImages;
    }

    public List<Long> getToDeleteImages() {
        return toDeleteImages;
    }

    public void setToDeleteImages(List<Long> toDeleteImages) {
        this.toDeleteImages = toDeleteImages;
    }


    @Override
    public String toString() {
        return "PendingAccommodationWholeEditedDTO{" +
                "toDeleteImages=" + toDeleteImages +
                ", accommodationId=" + accommodationId +
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
