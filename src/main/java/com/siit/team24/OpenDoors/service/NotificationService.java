package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.notification.NotificationDTO;
import com.siit.team24.OpenDoors.dto.notification.NotificationShowDTO;
import com.siit.team24.OpenDoors.model.Notification;
import com.siit.team24.OpenDoors.model.enums.NotificationType;
import com.siit.team24.OpenDoors.repository.NotificationRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repo;

    @Autowired
    private UserService userService;

    public List<NotificationShowDTO> findAllByUserId(Long userId) {
        return repo.findAllByUserId(userId);
    }

    public void add(NotificationDTO dto) {
        Notification notification = new Notification(dto.getId(), dto.getTimestamp(),
                userService.findByUsername(dto.getUsername()), dto.getMessage(),
                NotificationType.fromString(dto.getType()));
        repo.save(notification);
    }

}
