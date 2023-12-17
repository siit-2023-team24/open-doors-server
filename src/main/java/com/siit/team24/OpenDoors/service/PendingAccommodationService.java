package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationWholeDTO;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.repository.PendingAccommodationRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;

@Service
public class PendingAccommodationService {

    @Autowired
    private PendingAccommodationRepository repo;

    public PendingAccommodation findById(Long id) {
        Optional<PendingAccommodation> accommodation =  repo.findById(id);
        if (accommodation.isEmpty())
            throw new EntityNotFoundException();
        return accommodation.get();
    }

    public PendingAccommodation save(PendingAccommodation pendingAccommodation) {
        return repo.save(pendingAccommodation);
    }

    public PendingAccommodation update(PendingAccommodation newData) {
        PendingAccommodation accommodation = findById(newData.getId());
        return repo.save(newData);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Collection<PendingAccommodationHostDTO> getForHost(Long hostId) {
        return repo.findByHost(hostId);
    }

}
