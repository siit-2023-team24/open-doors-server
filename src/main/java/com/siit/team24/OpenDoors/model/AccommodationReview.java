package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Entity;

@Entity
public class AccommodationReview extends Review {

    //change
    private Long accommodation;

    private boolean approved;


    //constructor, get, set u sve 3
}
