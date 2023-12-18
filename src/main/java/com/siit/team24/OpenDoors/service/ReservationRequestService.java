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
        Timestamp today = new Timestamp(System.currentTimeMillis());
        return !repo.getActiveFor(accommodationId, today).isEmpty();
    }

    public int countConfirmedFutureFor(Long accommodationId) {
        Timestamp today = new Timestamp(System.currentTimeMillis());
        List<ReservationRequest> confirmed = repo.getConfirmedFutureFor(accommodationId, today);
        return confirmed.size();
    }

    public void denyAllFor(Long accommodationId) {
        List<ReservationRequest> pending =  repo.getPendingFor(accommodationId);
        for (ReservationRequest request: pending) {
            request.setStatus(ReservationRequestStatus.DENIED);
            repo.save(request);
        }
    }
}
