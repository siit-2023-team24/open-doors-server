package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.accommodation.AccommodationSearchDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestForGuestDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestSearchAndFilterDTO;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import org.springframework.stereotype.Service;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;
import java.util.ArrayList;
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

    public List<ReservationRequestForGuestDTO> findByGuestId(Long guestId) {
        List<ReservationRequest> allRequests = repo.findAll();
        List<ReservationRequestForGuestDTO> dtos = new ArrayList<>();

        for(ReservationRequest r: allRequests) {
            if(r.getGuest().getId().equals(guestId)) {
                dtos.add(new ReservationRequestForGuestDTO(r));
            }
        }

        return dtos;
    }

    public List<ReservationRequestForGuestDTO> searchRequests(Long guestId, ReservationRequestSearchAndFilterDTO searchAndFilterDTO) {
        List<ReservationRequestForGuestDTO> guestRequests = findByGuestId(guestId);
        List<ReservationRequestForGuestDTO> filteredRequests = new ArrayList<>();

        for(ReservationRequestForGuestDTO r: guestRequests) {
            if(searchAndFilterDTO.getAccommodationName() != null && !r.getAccommodationName().equals(searchAndFilterDTO.getAccommodationName()))
                continue;
            if(!isInDateRange(r, searchAndFilterDTO.getStartDate(), searchAndFilterDTO.getEndDate()))
                continue;
            if(searchAndFilterDTO.getStatus() != null && !r.getStatus().equals(searchAndFilterDTO.getStatus()))
                continue;

            filteredRequests.add(r);
        }

        return filteredRequests;
    }

    private boolean isInDateRange(ReservationRequestForGuestDTO request, Timestamp startDate, Timestamp endDate) {
        if(startDate != null && endDate != null) {
            if(request.getStartDate().before(startDate) || request.getEndDate().after(endDate))
                return false;
        } else if(startDate != null) {
            if(request.getStartDate().before(startDate))
                return false;
        } else if(endDate != null) {
            if(request.getEndDate().after(endDate))
                return false;
        }

        return true;
    }
}
