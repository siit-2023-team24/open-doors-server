package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.model.ReservationRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId" +
            " and (r.status = 0 or r.status = 1) and r.dateRange.endDate > :today")
    List<ReservationRequest> getActiveFor(Long accommodationId, Timestamp today);

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId" +
            " and r.status = 1 and r.dateRange.endDate > :today")
    List<ReservationRequest> getConfirmedFutureFor(Long accommodationId, Timestamp today);

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId and r.status = 0")
    List<ReservationRequest> getPendingFor(Long accommodationId);
}
