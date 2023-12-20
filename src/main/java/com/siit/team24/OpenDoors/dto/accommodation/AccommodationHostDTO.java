package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Image;
import com.siit.team24.OpenDoors.model.PendingAccommodation;

import java.sql.Array;
import java.util.ArrayList;

public class AccommodationHostDTO {
    private Long id;
    private Long image;
    private String name;

    public AccommodationHostDTO() {}

    public AccommodationHostDTO(Long id, Long image, String name) {
        this.id = id;
        this.image = image;
        this.name = name;
    }

    public AccommodationHostDTO(Accommodation accommodation) {
        this();
        id = accommodation.getId();
        name = accommodation.getName();
        image = null;
        if (!accommodation.getImages().isEmpty()) {
            image = new ArrayList<>(accommodation.getImages()).get(0).getId();
        }
    }

    public AccommodationHostDTO(PendingAccommodation accommodation) {
        this();
        id = accommodation.getId();
        name = accommodation.getName();
        image = null;
        if (!accommodation.getImages().isEmpty()) {
            image = new ArrayList<>(accommodation.getImages()).get(0).getId();
        }
    }

    public Long getId() {
        return id;
    }

    public Long getImage() {
        return image;
    }

    public void setImage(Long image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AccommodationHostDTO{" +
                "id=" + id +
                ", image=" + image +
                ", name='" + name + '\'' +
                '}';
    }
}
