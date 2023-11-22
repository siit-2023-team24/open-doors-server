package com.siit.team24.OpenDoors.dto;

import com.siit.team24.OpenDoors.model.Accommodation;

public class AccommodationHostDTO {
    private String image;
    private String name;

    public AccommodationHostDTO() {}

    public AccommodationHostDTO(Accommodation accommodation) {
        this(accommodation.getImages().get(0), accommodation.getName());
    }

    public AccommodationHostDTO(String image, String name) {
        this.image = image;
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
