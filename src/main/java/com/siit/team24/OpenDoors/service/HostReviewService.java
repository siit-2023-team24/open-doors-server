package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.review.ReviewDetailsDTO;
import com.siit.team24.OpenDoors.model.HostReview;
import com.siit.team24.OpenDoors.repository.HostReviewRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class HostReviewService {

    @Autowired
    private HostReviewRepository hostReviewRepository;

    public List<ReviewDetailsDTO> findAllForHost(Long hostId) {
        List<HostReview> reviews = hostReviewRepository.findAllByHostId(hostId);
        List<ReviewDetailsDTO> dtos = new ArrayList<>();
        for (HostReview hr : reviews) {
            dtos.add(new ReviewDetailsDTO(hr));
        }
        return dtos;
    }
}
