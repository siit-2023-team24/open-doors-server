package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.review.ReportedHostReviewDTO;
import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;
import com.siit.team24.OpenDoors.model.HostReview;
import com.siit.team24.OpenDoors.repository.HostReviewRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class HostReviewService {

    @Autowired
    private HostReviewRepository hostReviewRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;

    public List<ReviewDetailsDTO> findAllForHost(Long hostId) {
        List<HostReview> reviews = hostReviewRepository.findAllByHostId(hostId);
        List<ReviewDetailsDTO> dtos = new ArrayList<>();
        for (HostReview hr : reviews) {
            dtos.add(new ReviewDetailsDTO(hr));
        }
        dtos.sort(Comparator.comparing(ReviewDetailsDTO::getTimestamp).reversed());
        return dtos;
    }

    public List<ReportedHostReviewDTO> findAllReported() {
        List<ReportedHostReviewDTO> reported = hostReviewRepository.findAllReported();
        reported.sort(Comparator.comparing(ReportedHostReviewDTO::getTimestamp).reversed());
        return hostReviewRepository.findAllReported();
    }

    public boolean isReviewable(Long hostId, Long guestId) {
        boolean hasNotYetReviewed = hostReviewRepository.findByHostAndAuthor(hostId, guestId).isEmpty();
        boolean wasHosted = reservationRequestService.wasHosted(guestId, hostId);
        return (hasNotYetReviewed && wasHosted);
    }

    public void save(HostReview review) {
        this.hostReviewRepository.save(review);
    }

    public void delete(Long id) { this.hostReviewRepository.deleteById(id); }

    public void deleteAllForHost(Long hostId) {
        List<HostReview> reviews = hostReviewRepository.findAllByHostId(hostId);
        hostReviewRepository.deleteAll(reviews);
    }

    public void changeReportedStatus(Long id) {
        Optional<HostReview> foundReview = hostReviewRepository.findById(id);
        if (foundReview.isEmpty())
            throw new EntityNotFoundException();
        HostReview review = foundReview.get();
        review.setReported(!review.isReported());
        hostReviewRepository.save(review);
    }
}
