package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.repository.ReservationRequestRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReservationRequestService {

    @Autowired
    private ReservationRequestRepository reservationRequestRepository;
    @Autowired
    private UserService userService;

    public ReservationRequest save(ReservationRequest reservationRequest) {
        return reservationRequestRepository.save(reservationRequest);
    }
}
