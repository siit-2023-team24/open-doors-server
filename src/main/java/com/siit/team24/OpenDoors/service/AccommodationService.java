package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.exception.ActiveReservationRequestsFoundException;
import com.siit.team24.OpenDoors.exception.ExistingReservationsException;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Host;
import com.siit.team24.OpenDoors.repository.AccommodationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class AccommodationService {

    @Autowired
    private AccommodationRepository accommodationRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;

    public Accommodation findById(Long id) {
        Optional<Accommodation> accommodation = accommodationRepository.findById(id);
        if (accommodation.isEmpty()) {
            throw new EntityNotFoundException();
        }
        return accommodation.get();
    }

    public Accommodation save (Accommodation accommodation) {
        Accommodation newAccommodation = accommodationRepository.save(accommodation);
        System.out.println("New accommodation: " + newAccommodation);
        return newAccommodation;
    }

    public void delete(Long id) {
        int reservationsNumber = reservationRequestService.countConfirmedFutureFor(id);
        if (reservationsNumber != 0) {
            throw new ExistingReservationsException();
        }
        reservationRequestService.denyAllFor(id);
        accommodationRepository.deleteById(id);
    }

    public void deleteForEdit(Long id) {
        boolean found = reservationRequestService.foundActiveFor(id);
        if (found) throw new ActiveReservationRequestsFoundException();
        accommodationRepository.deleteById(id);
    }

    public void revive(Long id) {
        accommodationRepository.revive(id);
    }

    public Collection<AccommodationHostDTO> getForHost(Long hostId) {
        return accommodationRepository.findAllDtoByHostId(hostId);
    }

    public List<Accommodation> findAllByHostId(Long hostId) {
        return accommodationRepository.findAllByHostId(hostId);
    }

}
