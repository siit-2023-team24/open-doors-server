package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.sql.Timestamp;
import java.util.List;

public interface ReservationRequestRepository extends JpaRepository<ReservationRequest, Long> {

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId" +
            " and (r.status = 0 or r.status = 1) and r.dateRange.startDate > current_timestamp")
    List<ReservationRequest> getActiveFor(Long accommodationId);

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId" +
            " and r.status = 1 and r.dateRange.endDate > current_timestamp")
    List<ReservationRequest> getConfirmedFutureFor(Long accommodationId);

    @Query("select r from ReservationRequest r where r.accommodation.id = :accommodationId and r.status = 0")
    List<ReservationRequest> getPendingFor(Long accommodationId);

    @Query("select r from ReservationRequest r where r.guest.username = :guestUsername and r.status = :status " +
            "and r.dateRange.startDate > current_timestamp ")
    List<ReservationRequest> getFutureForGuestWithStatus(String guestUsername, ReservationRequestStatus status);


    @Query("select r from ReservationRequest r where r.accommodation.host.id = :hostId")
    List<ReservationRequest> findByHost(Long hostId);

    @Query("select count(r) from ReservationRequest r where r.guest.id = :guestId and r.status = 3")
    int countCancelledBy(Long guestId);

    @Query("select r from ReservationRequest r where r.guest.id = ?1 and r.accommodation.host.id = ?2 and r.status = 1 " +
            "and r.dateRange.endDate < current_timestamp ")
    List<ReservationRequest> getPastForGuestAndHostConfirmed(Long guestId, Long hostId);

    @Query("select r from ReservationRequest r where r.guest.id = ?1 and r.accommodation.id = ?2 and r.status = 1 " +
            "and r.dateRange.endDate < current_timestamp and r.dateRange.endDate > ?3")
    List<ReservationRequest> getPastForGuestAndAccommodationConfirmed(Long guestId, Long hostId, Timestamp deadline);

    @Query("select distinct r.guest.id from ReservationRequest r where r.accommodation.host.id = ?1 and r.id not in ?2 and r.status = 1 " +
            "and r.dateRange.endDate < current_timestamp")
    List<Long> getGuestsByHostId(Long hostId, List<Long> evidencedReservationIds);

    @Query("select distinct r.accommodation.host.id from ReservationRequest r where r.guest.id = ?1 and r.id not in ?2 and r.status = 1 " +
            "and r.dateRange.endDate < current_timestamp")
    List<Long> getHostsByGuestId(Long guestId, List<Long> evidencedReservationIds);

    @Query("select r from ReservationRequest r where r.accommodation.host.id = ?1 and r.guest.id = ?2")
    List<ReservationRequest> findByHostAndGuest(Long hostId, Long guestId);
}
