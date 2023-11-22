package com.siit.team24.OpenDoors.Domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ElementCollection;


import lombok.Getter;

import java.util.List;

@Getter
@Entity
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String description;
    private String location;
    @ElementCollection
    private final List<Long> amenities;
    @ElementCollection
    private final List<DateRange> availability;

    private int minGuests;
    private int maxGuests;

    public Accommodation(Long id, String name, String description, String location, List<Long> amenities, List<DateRange> availability, int minGuests, int maxGuests) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.location = location;
        this.amenities = amenities;
        this.availability = availability;
        this.minGuests = minGuests;
        this.maxGuests = maxGuests;
    }
}
