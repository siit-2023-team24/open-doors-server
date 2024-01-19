package com.siit.team24.OpenDoors.repository;

import com.siit.team24.OpenDoors.dto.notification.NotificationShowDTO;
import com.siit.team24.OpenDoors.model.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface NotificationRepository extends JpaRepository<Notification, Long> {

    @Query("select new com.siit.team24.OpenDoors.dto.notification.NotificationShowDTO(n) from Notification n where n.user.id = :userId")
    List<NotificationShowDTO> findAllByUserId(Long userId);
}
