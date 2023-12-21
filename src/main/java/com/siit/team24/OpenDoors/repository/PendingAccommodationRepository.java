package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO;
import com.siit.team24.OpenDoors.model.PendingAccommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface PendingAccommodationRepository extends JpaRepository<PendingAccommodation, Long> {

    @Query("select new com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO(a) from PendingAccommodation a")
    Collection<PendingAccommodationHostDTO> findAllDtos();

    @Query("select new com.siit.team24.OpenDoors.dto.pendingAccommodation.PendingAccommodationHostDTO(a) from PendingAccommodation a where a.host.id =:hostId")
    Collection<PendingAccommodationHostDTO> findByHost(Long hostId);

    @Query("select a from PendingAccommodation a where a.host.id =:hostId")
    List<PendingAccommodation> findAllByHostId(Long hostId);
}
