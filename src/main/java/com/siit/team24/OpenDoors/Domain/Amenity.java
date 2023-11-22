package com.siit.team24.OpenDoors.Domain;

import jakarta.persistence.Embeddable;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import lombok.Getter;

@Getter
@Embeddable
public class Amenity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    public Amenity(Long id, String name) {
        this.id = id;
        this.name = name;
    }
}
