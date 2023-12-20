package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;

public interface AccommodationRepository extends JpaRepository<Accommodation, Long> {

    @Query("select new com.siit.team24.OpenDoors.dto.accommodation.AccommodationHostDTO(a) from Accommodation a where a.host.id =:hostId")
    Collection<AccommodationHostDTO> findByHost(Long hostId);


    @Query(value = "UPDATE accommodation SET deleted = false WHERE id = ?1", nativeQuery = true)
    @Modifying
    @Transactional
    void revive(Long id);
}
