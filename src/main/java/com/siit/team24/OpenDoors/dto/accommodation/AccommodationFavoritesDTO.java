package com.siit.team24.OpenDoors.dto.accommodation;

import jakarta.validation.constraints.Min;

public class AccommodationFavoritesDTO {
    @Min(1)
    private Long accommodationId;
    @Min(1)
    private Long guestId;

    public AccommodationFavoritesDTO(Long accommodationId, Long guestId) {
        this.accommodationId = accommodationId;
        this.guestId = guestId;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
    }

    public Long getGuestId() {
        return guestId;
    }

    public void setGuestId(Long guestId) {
        this.guestId = guestId;
    }

    @Override
    public String toString() {
        return "AccommodationFavoritesDTO{" +
                "accommodationId=" + accommodationId +
                ", guestId=" + guestId +
                '}';
    }
}
