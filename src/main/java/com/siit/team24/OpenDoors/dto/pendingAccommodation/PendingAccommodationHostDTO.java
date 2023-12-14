package com.siit.team24.OpenDoors.dto.pendingAccommodation;

import com.siit.team24.OpenDoors.model.PendingAccommodation;

import java.util.ArrayList;

public class PendingAccommodationHostDTO {

    private Long id;
    private Long accommodationId;
    private Long image;
    private String name;

    public PendingAccommodationHostDTO() {
    }

    public PendingAccommodationHostDTO(Long id, Long accommodationId, Long image, String name) {
        this.id = id;
        this.accommodationId = accommodationId;
        this.image = image;
        this.name = name;
    }

    public PendingAccommodationHostDTO(PendingAccommodation accommodation) {
        this();
        id = accommodation.getId();
        accommodationId = accommodation.getAccommodationId();
        name = accommodation.getName();
        image = null;
        if (!accommodation.getImages().isEmpty()){
            image = new ArrayList<>(accommodation.getImages()).get(0).getId();
        }
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getAccommodationId() {
        return accommodationId;
    }

    public void setAccommodationId(Long accommodationId) {
        this.accommodationId = accommodationId;
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
        return "PendingAccommodationHostDTO{" +
                "id=" + id +
                ", accommodationId=" + accommodationId +
                ", image=" + image +
                ", name='" + name + '\'' +
                '}';
    }
}
