package com.siit.team24.OpenDoors.service.user;

import com.siit.team24.OpenDoors.dto.userManagement.NewUserReportDTO;
import com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO;
import com.siit.team24.OpenDoors.model.ReservationRequest;
import com.siit.team24.OpenDoors.model.UserReport;
import com.siit.team24.OpenDoors.model.enums.UserReportStatus;
import com.siit.team24.OpenDoors.repository.user.UserReportRepository;
import com.siit.team24.OpenDoors.service.PendingAccommodationService;
import com.siit.team24.OpenDoors.service.ReservationRequestService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserReportService {

    @Autowired
    private UserReportRepository userReportRepository;

    @Autowired
    private ReservationRequestService reservationRequestService;

    @Autowired
    private UserService userService;

    @Autowired
    private PendingAccommodationService pendingAccommodationService;

    public UserReport findById(Long id) {
        Optional<UserReport> report = userReportRepository.findById(id);
        if (report.isEmpty())
            throw new EntityNotFoundException();
        return report.get();
    }

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
        reports.sort(Comparator.comparing(UserReportDTO::getTimestamp).reversed());
        return reports;
    }

    public UserReportDTO dismiss(Long id) {
        UserReport report = findById(id);
        if (report.getStatus() != UserReportStatus.ACTIVE)
            throw new IllegalArgumentException("Cannot dismiss a report that is not active");
        report.setStatus(UserReportStatus.DISMISSED);
        report = userReportRepository.save(report);
        return new UserReportDTO(report);
    }

    public void resolve(Long id) {
        UserReport report = findById(id);
        userService.block(report.getRecipient().getId(), report.getRecipient().getRole());
        pendingAccommodationService.deleteAllForHost(report.getId());
        resolveAllFor(report.getRecipient().getId());
    }

    private void resolveAllFor(Long recipientId) {
        List<UserReport> reports = userReportRepository.findAllByRecipientId(recipientId);
        for (UserReport report: reports) {
            if (report.getStatus() == UserReportStatus.ACTIVE) {
                report.setStatus(UserReportStatus.RESOLVED);
                userReportRepository.save(report);
            }
        }
    }

    public void deleteAllFor(Long recipientId) {
        List<UserReport> reports = userReportRepository.findAllByRecipientId(recipientId);
        userReportRepository.deleteAll(reports);
    }
}
