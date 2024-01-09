package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.model.HostReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface HostReviewRepository extends JpaRepository<HostReview, Long> {
    @Query("select hr from HostReview hr where hr.host.id = ?1")
    public List<HostReview> findAllByHostId(Long hostId);

    @Query("select hr from HostReview hr where hr.host.id = ?1 and hr.author.id = ?2")
    public Optional<HostReview> findByHostAndAuthor(Long hostId, Long guestId);
}
