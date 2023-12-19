package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationWholeDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Host;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.repository.PendingAccommodationRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
public class PendingAccommodationService {

    @Autowired
    private PendingAccommodationRepository repo;

    @Autowired
    private AccommodationService accommodationService;


    public PendingAccommodation findById(Long id) {
        Optional<PendingAccommodation> accommodation = repo.findById(id);
        if (accommodation.isEmpty())
            throw new EntityNotFoundException();
        return accommodation.get();
    }

    public PendingAccommodation save(PendingAccommodation pendingAccommodation) {
        if (pendingAccommodation.getId() == null && pendingAccommodation.getAccommodationId() != null) { //editing active accommodation
            accommodationService.deleteForEdit(pendingAccommodation.getAccommodationId());
        }
        return repo.save(pendingAccommodation);
    }

    public void delete(Long id) {
        PendingAccommodation pending = findById(id);
        if (pending.getAccommodationId() != null) {
            accommodationService.revive(pending.getAccommodationId());
        }
        repo.deleteById(id);
    }

    public void deleteAllForHost(Long hostId) {
        List<PendingAccommodation> accommodations = repo.findAllByHostId(hostId);
        repo.deleteAll(accommodations);
    }

    public Collection<PendingAccommodationHostDTO> getAll() {
        return repo.findAllDtos();
    }

    public Collection<PendingAccommodationHostDTO> getForHost(Long hostId) {
        return repo.findByHost(hostId);
    }

    public void approve(PendingAccommodationHostDTO dto) {
        PendingAccommodation pendingAccommodation = findById(dto.getId());
        System.out.println(pendingAccommodation);
        AccommodationWholeDTO accommodationWholeDTO = new AccommodationWholeDTO(pendingAccommodation);

        System.out.println(accommodationWholeDTO);

        this.delete(dto.getId());

        Accommodation accommodation = new Accommodation();
        accommodation.setSimpleValues(accommodationWholeDTO);

        Host host = pendingAccommodation.getHost();
        accommodation.setHost(host);

        accommodation.setImages(pendingAccommodation.getImages());



        if (dto.getAccommodationId() != null) {
            Accommodation oldData = accommodationService.findById(dto.getAccommodationId());
            accommodation.setAverageRating(oldData.getAverageRating());
        }


        Accommodation saved = accommodationService.save(accommodation);
        System.out.println("Saved to accommodations: " + saved);

    }

}
