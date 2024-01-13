package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.model.Guest;
import com.siit.team24.OpenDoors.model.Host;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.UserReport;
import com.siit.team24.OpenDoors.repository.user.UserReportRepository;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class UserReportService {

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @Autowired
    private UserService userService;

    public List<String> getReportableUsers(Long userId, boolean isGuestComplainant){

        List<Long> evidencedReservationIds = new ArrayList<Long>();
        evidencedReservationIds.add(-1L);
        for(UserReport userReport : userReportRepository.findAllByComplainantId(userId)) {
            for(ReservationRequest reservationRequest: userReport.getEvidence()) {
                evidencedReservationIds.add(reservationRequest.getId());
            }
        }

        List<Long> reportableUserIds = this.reservationRequestService.getReportableUserIds(userId, evidencedReservationIds, isGuestComplainant);
        reportableUserIds.add(-1L);
        return this.userService.getUsernames(reportableUserIds);
    }
}
