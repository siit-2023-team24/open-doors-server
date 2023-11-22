package com.siit.team24.OpenDoors.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;

import java.sql.Timestamp;

@Entity
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public abstract class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Long id;

    @Min(1)
    @Max(5)
    private int rating;
    private String comment;
    private Timestamp timestamp;

    //change to user after merging
    private String author;
}
