package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.dto.review.PendingAccommodationReviewDetailsDTO;
import com.siit.team24.OpenDoors.model.AccommodationReview;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview, Long> {
    @Query("select ar from AccommodationReview ar where ar.accommodation.id = ?1 and ar.approved = true")
    List<AccommodationReview> findAllByAccommodationIdApproved(Long accommodationId);

    @Query("select ar from AccommodationReview ar where ar.accommodation.id = ?1 and ar.author.id = ?2")
    List<AccommodationReview> findByAccommodationAndAuthor(Long accommodationId, Long guestId);

    @Query("select avg(ar.rating) from AccommodationReview ar where ar.accommodation.id = ?1 and ar.approved = true")
    Double getAverageRating(Long accommodationId);

    @Query("select ar from AccommodationReview ar where ar.author.id=?1 and ar.approved=false")
    List<AccommodationReview> findByGuestId(Long guestId);

    @Query("select new com.siit.team24.OpenDoors.dto.review.PendingAccommodationReviewDetailsDTO(r) from AccommodationReview r where r.approved=false")
    List<PendingAccommodationReviewDetailsDTO> findAllPending();

}
