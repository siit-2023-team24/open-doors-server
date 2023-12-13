package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    public Accommodation save (Accommodation accommodation) {
        System.out.println("Accommodation to be saved: " + accommodation.toString());

        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        System.out.println("New accommodation: " + newAccommodation.toString());
        return newAccommodation;
    }

}
