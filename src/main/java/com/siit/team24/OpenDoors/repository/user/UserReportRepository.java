package com.siit.team24.OpenDoors.repository.user;

import com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO;
import com.siit.team24.OpenDoors.model.UserReport;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface UserReportRepository extends JpaRepository<UserReport, Long> {
    @Query("select ur from UserReport ur where ur.complainant.id = ?1")
    List<UserReport> findAllByComplainantId(Long userId);

    @Query("select ur from UserReport ur where ur.recipient.id = ?1")
    List<UserReport> findAllByRecipientId(Long userId);

    @Query("select new com.siit.team24.OpenDoors.dto.userManagement.UserReportDTO(r) from UserReport r")
    List<UserReportDTO> findAllDTOs();
}
