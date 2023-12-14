package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.repository.AccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;


    public Accommodation save (Accommodation accommodation) {
        System.out.println("I SAVED IT" + accommodation.toString());
        return accommodationRepository.save(accommodation);
    }

    public Collection<AccommodationHostDTO> getForHost(Long hostId) {
        return accommodationRepository.findByHost(hostId);
    }

}
