package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import com.siit.team24.OpenDoors.repository.PendingAccommodationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;

@Service
public class PendingAccommodationService {

    @Autowired
    private PendingAccommodationRepository repo;

    public PendingAccommodation save(PendingAccommodation pendingAccommodation) {
        return repo.save(pendingAccommodation);
    }

    public void delete(Long id) {
        repo.deleteById(id);
    }

    public Collection<PendingAccommodationHostDTO> findForHost(Long hostId) {
        return repo.findByHost(hostId);
    }

}
