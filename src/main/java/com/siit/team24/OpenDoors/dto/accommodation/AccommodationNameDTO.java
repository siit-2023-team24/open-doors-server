package com.siit.team24.OpenDoors.dto.accommodation;

import com.siit.team24.OpenDoors.model.Accommodation;

public class AccommodationNameDTO {
    private Long id;
    private String name;

    public AccommodationNameDTO(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public AccommodationNameDTO(Accommodation accommodation) {
        this.id = accommodation.getId();
        this.name = accommodation.getName();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "AccommodationNameDTO{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
