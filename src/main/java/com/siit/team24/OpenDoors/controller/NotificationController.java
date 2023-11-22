package com.siit.team24.OpenDoors.controller;

import com.siit.team24.OpenDoors.dto.NotificationDTO;
import com.siit.team24.OpenDoors.model.Notification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotificationController {
    @GetMapping(value = "/{id}")
    public ResponseEntity<NotificationDTO> getNotificationsByUserID(@PathVariable Long id) {
        return new ResponseEntity<NotificationDTO>(new NotificationDTO(new Notification()), HttpStatus.OK);
    }

}
