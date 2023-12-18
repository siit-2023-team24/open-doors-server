package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.repository.AccommodationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class AccommodationService {
    @Autowired
    private AccommodationRepository accommodationRepository;

    public Accommodation findById(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        if (accommodation.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return accommodation.get();
    }

    public Accommodation save (Accommodation accommodation) {
        System.out.println("Accommodation to be saved: " + accommodation.toString());

        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        System.out.println("New accommodation: " + newAccommodation);
        return newAccommodation;
    }

    public void delete(Long id) {
        //TODO check if there are any reservations confirmed for this accommodation in the future
        //throw exception if so
        accommodationRepository.deleteById(id);
    }

    public void revive(Long id) {
//        Accommodation accommodation = findById(id);
//        accommodation.setDeleted(false);
//        save(accommodation);
        accommodationRepository.revive(id);
    }

    public Collection<AccommodationHostDTO> getForHost(Long hostId) {
        return accommodationRepository.findByHost(hostId);
    }

}
