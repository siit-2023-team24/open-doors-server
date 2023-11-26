package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Image;

public class AccommodationHostDTO {
    private Image image;
    private String name;

    public AccommodationHostDTO() {}

    public AccommodationHostDTO(Accommodation accommodation) {
        this((Image) accommodation.getImages().toArray()[0], accommodation.getName());
    }

    public AccommodationHostDTO(Image image, String name) {
        this.image = image;
        this.name = name;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
