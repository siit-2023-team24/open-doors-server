package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.stereotype.Service;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository repo;

    public ReservationRequest save(ReservationRequest reservationRequest) {
        return repo.save(reservationRequest);
    }

    public boolean foundActiveFor(Long accommodationId) {
        return !repo.getActiveFor(accommodationId).isEmpty();
    }

    public int countConfirmedFutureFor(Long accommodationId) {
        List<ReservationRequest> confirmed = repo.getConfirmedFutureFor(accommodationId);
        return confirmed.size();
    }

    public void denyAllFor(Long accommodationId) {
        List<ReservationRequest> pending =  repo.getPendingFor(accommodationId);
        for (ReservationRequest request: pending) {
            request.setStatus(ReservationRequestStatus.DENIED);
            repo.save(request);
        }
    }

    public List<ReservationRequest> findByUsernameAndStatus(String guestUsername, ReservationRequestStatus status) {
        return repo.getFutureForGuestWithStatus(guestUsername, status);
    }

    public void deletePendingForGuest(String username) {
        List<ReservationRequest> requests = findByUsernameAndStatus(username, ReservationRequestStatus.PENDING);
        repo.deleteAll(requests);
    }
}
