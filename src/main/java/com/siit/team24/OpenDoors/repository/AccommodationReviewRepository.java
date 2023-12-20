package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.model.AccommodationReview;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AccommodationReviewRepository extends JpaRepository<AccommodationReview, Long> {

}
