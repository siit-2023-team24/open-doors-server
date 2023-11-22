package com.siit.team24.OpenDoors.model;

import jakarta.persistence.Entity;

@Entity
public class HostReview extends Review {

    //change
    private String host;

    private boolean reported;


}
