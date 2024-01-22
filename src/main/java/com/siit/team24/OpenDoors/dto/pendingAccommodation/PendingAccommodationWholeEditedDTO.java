package com.siit.team24.OpenDoors.dto.pendingAccommodation;

import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.model.SeasonalRate;

import java.util.List;
import java.util.Set;

public class PendingAccommodationWholeEditedDTO extends PendingAccommodationWholeDTO {

    private List<Long> toDeleteImages;

    public PendingAccommodationWholeEditedDTO() {
    }

    public PendingAccommodationWholeEditedDTO(PendingAccommodation accommodation, List<Long> toDeleteImages) {
        super(accommodation);
        this.toDeleteImages = toDeleteImages;
    }

    public PendingAccommodationWholeEditedDTO(
            Long id, String name, String description, String location, List<String> amenities, Set<Image> images, int minGuests, int maxGuests, String accommodationType, List< DateRange > availability, double price, boolean isPricePerGuest, List<
            SeasonalRate > seasonalRates, String city, String country, String street, int number, int deadline, boolean isAutomatic, String hostUsername, Long accommodationId, List<Long> toDeleteImages)
    {
        super(id, name, description, location, amenities, images, minGuests, maxGuests, accommodationType, availability, price, isPricePerGuest,
                seasonalRates, city, country, street, number, deadline, isAutomatic, hostUsername, accommodationId);
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
