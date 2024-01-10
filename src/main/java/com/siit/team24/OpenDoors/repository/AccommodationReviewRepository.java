package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.model.AccommodationReview;
import com.siit.team24.OpenDoors.model.HostReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview, Long> {
    @Query("select ar from AccommodationReview ar where ar.accommodation.id = ?1")
    public List<AccommodationReview> findAllByAccommodationId(Long accommodationId);

    @Query("select ar from AccommodationReview ar where ar.accommodation.id = ?1 and ar.author.id = ?2")
    public List<HostReview> findByAccommodationAndAuthor(Long accommodationId, Long guestId);

}
