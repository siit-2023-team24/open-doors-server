package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.dto.userManagement.NewUserReportDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.UserReport;
import com.siit.team24.OpenDoors.repository.user.UserReportRepository;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
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
                if(evidencedReservationIds.contains(reservationRequest.getId())) continue;
                evidencedReservationIds.add(reservationRequest.getId());
            }
        }

        List<Long> reportableUserIds = this.reservationRequestService.getReportableUserIds(userId, evidencedReservationIds, isGuestComplainant);
        reportableUserIds.add(-1L);
        return this.userService.getUsernames(reportableUserIds);
    }

    public UserReport createReport(NewUserReportDTO dto) {
        UserReport report = new UserReport(dto);
        report.setComplainant(userService.findByUsername(dto.getComplainantUsername()));
        report.setRecipient(userService.findByUsername(dto.getRecipientUsername()));
        List<ReservationRequest> evidence;
        if (dto.getIsComplainantGuest()) { evidence = reservationRequestService.findByHostAndGuest(report.getRecipient().getId(), report.getComplainant().getId()); }
        else { evidence = reservationRequestService.findByHostAndGuest(report.getComplainant().getId(), report.getRecipient().getId()); }
        report.setEvidence(new HashSet<>(evidence));
        report = userReportRepository.save(report);
        return report;
    }

    public List<UserReportDTO> findAllDTOs() {
        List<UserReportDTO> reports = userReportRepository.findAllDTOs();
        reports.sort(Comparator.comparing(UserReportDTO::getTimestamp));
        return reports;
    }
}
