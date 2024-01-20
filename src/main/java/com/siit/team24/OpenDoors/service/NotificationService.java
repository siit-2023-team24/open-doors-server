package com.siit.team24.OpenDoors.service;

import com.siit.team24.OpenDoors.dto.notification.NotificationDTO;
import com.siit.team24.OpenDoors.dto.notification.NotificationShowDTO;
import com.siit.team24.OpenDoors.model.Notification;
import com.siit.team24.OpenDoors.model.User;
import com.siit.team24.OpenDoors.model.enums.NotificationType;
import com.siit.team24.OpenDoors.repository.NotificationRepository;
import com.siit.team24.OpenDoors.service.user.UserService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository repo;

    @Autowired
    private UserService userService;

    public Notification findById(Long id) {
        Optional<Notification> notification = repo.findById(id);
        if (notification.isEmpty())
            throw new EntityNotFoundException();
        return notification.get();
    }

    public List<NotificationShowDTO> findAllByUserId(Long userId) {
        return repo.findAllByUserId(userId);
    }

    public void add(NotificationDTO dto) {
        Notification notification = new Notification(dto.getId(), dto.getTimestamp(),
                userService.findByUsername(dto.getUsername()), dto.getMessage(),
                dto.getType());
        repo.save(notification);
    }

    public boolean isEnabled(String username, NotificationType type) {
        User user = userService.findByUsername(username);
        if (user.getDisabledTypes() == null) return true;
        return !user.getDisabledTypes().contains(type);

    }
}
