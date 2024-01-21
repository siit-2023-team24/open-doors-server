package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestForGuestDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestForHostDTO;
import com.siit.team24.OpenDoors.dto.reservation.ReservationRequestSearchAndFilterDTO;
import com.siit.team24.OpenDoors.exception.CancelRequestException;
import com.siit.team24.OpenDoors.model.Accommodation;
import com.siit.team24.OpenDoors.model.DateRange;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.siit.team24.OpenDoors.model.enums.ReservationRequestStatus;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.sql.Timestamp;

import java.util.*;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository repo;


    @Autowired
    private AccommodationService accommodationService;

    public List<ReservationRequest> findAll() { return repo.findAll(); }


    public ReservationRequest findById(Long requestId) {
        if(repo.findById(requestId).isPresent())
            return repo.findById(requestId).get();
        return null;
    }

    public ReservationRequest save(ReservationRequest reservationRequest) {
        return repo.save(reservationRequest);
    }

    public boolean foundActiveFor(Long accommodationId) {
        return !repo.getActiveFor(accommodationId).isEmpty();
    }

    public void denyActiveForAccommodation(Long accommodationId) {
        List<ReservationRequest> requests = repo.getActiveFor(accommodationId);
        for (ReservationRequest request: requests) {
            request.setStatus(ReservationRequestStatus.DENIED);
            repo.save(request);
        }
    }

    public boolean isAccommodationReadyForDelete(Long accommodationId) {
        List<ReservationRequest> confirmed = repo.getConfirmedFutureFor(accommodationId);
        return confirmed.isEmpty();
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

    public void cancelFutureForGuest(String username) {
        List<ReservationRequest> requests = repo.getFutureForGuestWithStatus(username, ReservationRequestStatus.CONFIRMED);
        for (ReservationRequest request: requests) {
            accommodationService.addToAvailability(request.getAccommodation().getId(), request.getDateRange());
            request.setStatus(ReservationRequestStatus.CANCELLED);
            repo.save(request);
        }
    }

    public List<ReservationRequestForGuestDTO> findByGuestId(Long guestId) {
        List<ReservationRequest> allRequests = repo.findAll();
        List<ReservationRequestForGuestDTO> dtos = new ArrayList<>();

        for(ReservationRequest r: allRequests) {
            if(r.getGuest().getId().equals(guestId)) {
                dtos.add(new ReservationRequestForGuestDTO(r));
            }
        }

        dtos.sort(Comparator.comparing(ReservationRequestForGuestDTO::getTimestamp).reversed());

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

    public List<ReservationRequestForHostDTO> searchRequestsForHost(Long hostId, ReservationRequestSearchAndFilterDTO searchAndFilterDTO) {
        List<ReservationRequest> requests = repo.findByHost(hostId);
        List<ReservationRequestForHostDTO> filteredRequests = new ArrayList<>();

        for(ReservationRequest request: requests) {
            if (passedFilter(request, searchAndFilterDTO))
                filteredRequests.add(convertToHostDTO(request));
        }
        filteredRequests.sort(Comparator.comparing(ReservationRequestForHostDTO::getTimestamp).reversed());
        return filteredRequests;
    }

    public List<ReservationRequestForHostDTO> getAllForHost(Long hostId) {
        List<ReservationRequest> requests = repo.findByHost(hostId);
        List<ReservationRequestForHostDTO> dtos = new ArrayList<>();
        for (ReservationRequest request: requests) {
            dtos.add(convertToHostDTO(request));
        }
        dtos.sort(Comparator.comparing(ReservationRequestForHostDTO::getTimestamp).reversed());
        return dtos;
    }

    private boolean passedFilter(ReservationRequest request, ReservationRequestSearchAndFilterDTO searchAndFilterDTO) {
        if (searchAndFilterDTO.getAccommodationName() != null && !request.getAccommodation().getName().toLowerCase().contains(searchAndFilterDTO.getAccommodationName().toLowerCase()))
            return false;

        if (searchAndFilterDTO.getStartDate() != null && searchAndFilterDTO.getEndDate() != null) {
            DateRange searchRange = new DateRange(searchAndFilterDTO.getStartDate(), searchAndFilterDTO.getEndDate());
            if (!searchRange.overlapsWith(request.getDateRange())) return false;

        } else if (searchAndFilterDTO.getStartDate() != null) {
            if (!request.getDateRange().contains(searchAndFilterDTO.getStartDate())) return false;

        } else if(searchAndFilterDTO.getEndDate() != null) {
            if (!request.getDateRange().contains(searchAndFilterDTO.getEndDate())) return false;
        }

        if(searchAndFilterDTO.getStatus() != null && !request.getStatus().equals(searchAndFilterDTO.getStatus()))
            return false;

        return true;
    }

    private ReservationRequestForHostDTO convertToHostDTO(ReservationRequest request) {
        ReservationRequestForHostDTO dto = new ReservationRequestForHostDTO(request);
        dto.setCancelledNumber(repo.countCancelledBy(request.getGuest().getId()));
        return dto;
    }

    public void delete(Long id) {
        ReservationRequest request = findById(id);
        if (request == null) throw new EntityNotFoundException();
        if (request.getStatus() != ReservationRequestStatus.PENDING)
            throw new IllegalArgumentException("Tried to delete a reservation request that was not pending.");

        request.setStatus(ReservationRequestStatus.DELETED);
        repo.save(request);
    }

    public void cancel(Long id) {
        ReservationRequest request = findById(id);
        if (request.getStatus() != ReservationRequestStatus.CONFIRMED)
            throw new IllegalArgumentException("Tried to cancel a reservation request that was not confirmed.");
        if (!request.isCancellable())
            throw new CancelRequestException();

        accommodationService.addToAvailability(request.getAccommodation().getId(), request.getDateRange());
        request.setStatus(ReservationRequestStatus.CANCELLED);
        repo.save(request);
    }

    public void deny(Long id) {
        Optional<ReservationRequest> foundRequest = repo.findById(id);
        if (foundRequest.isEmpty()) throw new EntityNotFoundException();

        ReservationRequest request = foundRequest.get();
        if (request.getStatus() != ReservationRequestStatus.PENDING)
            throw new IllegalArgumentException("Tried to deny a reservation request that was not pending.");

        request.setStatus(ReservationRequestStatus.DENIED);
        repo.save(request);
    }

    public void denyAllOverlappingRequests(Long accommodationId, DateRange dateRange) {
        List<ReservationRequest> pendingRequests = repo.getPendingFor(accommodationId);
        for (ReservationRequest request: pendingRequests) {
            if (dateRange.overlapsWith(request.getDateRange())) {
                request.setStatus(ReservationRequestStatus.DENIED);
                repo.save(request);
            }
        }
    }

    public void confirm(Long id) {
        Optional<ReservationRequest> foundRequest = repo.findById(id);
        if (foundRequest.isEmpty()) throw new EntityNotFoundException();

        ReservationRequest request = foundRequest.get();
        if (request.getStatus() != ReservationRequestStatus.PENDING)
            throw new IllegalArgumentException("Tried to confirm a reservation request that was not pending.");

        request.setStatus(ReservationRequestStatus.CONFIRMED);
        repo.save(request);
        denyAllOverlappingRequests(request.getAccommodation().getId(), request.getDateRange());
        accommodationService.removeDatesFromAccommodationAvailability(request.getAccommodation().getId(), request.getDateRange());
    }


    public boolean wasHosted(Long guestId, Long hostId) {
        List<ReservationRequest> stays = repo.getPastForGuestAndHostConfirmed(guestId, hostId);
        return (!stays.isEmpty());
    }

    //    For testing purposes, replace the deadline for reviewing an accommodation
    //    (7 days = 1000 * 60 * 60 * 24 * 7) with 7 minutes:
    //    1000 * 60 * 7
    public boolean hasStayed(Long guestId, Long accommodationId) {
        Timestamp deadline = new Timestamp(System.currentTimeMillis() -
                1000 * 60 * 60 * 24 * 20 -
                1000 * 60 * 60 * 24 * 20
        );
        List<ReservationRequest> stays = repo.getPastForGuestAndAccommodationConfirmed(guestId, accommodationId, deadline);
        return (!stays.isEmpty());
    }

    public List<Long> getReportableUserIds(Long userId, List<Long> evidencedReservationIds, boolean isGuestComplainant) {
        if (isGuestComplainant) return repo.getHostsByGuestId(userId, evidencedReservationIds);
        return repo.getGuestsByHostId(userId, evidencedReservationIds);
    }

    public List<ReservationRequest> findByHostAndGuest(Long hostId, Long guestId) { return this.repo.findByHostAndGuest(hostId, guestId); }
}
