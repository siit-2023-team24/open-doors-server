package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.Host;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Collection;
import java.util.List;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("select new com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO(a) from Accommodation a where a.host.id =:hostId")
    Collection<AccommodationHostDTO> findByHost(Long hostId);
}
