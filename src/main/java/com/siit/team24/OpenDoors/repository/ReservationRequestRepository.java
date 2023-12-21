package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId" +
            " and (r.status = 0 or r.status = 1) and r.dateRange.endDate > current_timestamp")
    List<ReservationRequest> getActiveFor(Long accommodationId);

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId" +
            " and r.status = 1 and r.dateRange.endDate > current_timestamp")
    List<ReservationRequest> getConfirmedFutureFor(Long accommodationId);

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId and r.status = 0")
    List<ReservationRequest> getPendingFor(Long accommodationId);

    @Query("select r from ReservationRequest r where r.guest.username = :guestUsername and r.status = :status " +
            "and r.dateRange.endDate > current_timestamp ")
    List<ReservationRequest> getFutureForGuestWithStatus(String guestUsername, ReservationRequestStatus status);
}
