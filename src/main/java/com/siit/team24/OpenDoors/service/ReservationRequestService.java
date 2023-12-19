package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository repo;

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
}
