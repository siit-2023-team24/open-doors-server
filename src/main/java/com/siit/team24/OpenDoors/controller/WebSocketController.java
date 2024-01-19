package com.siit.team24.OpenDoors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.siit.team24.OpenDoors.dto.notification.NotificationDTO;
import com.siit.team24.OpenDoors.model.enums.NotificationType;
import com.siit.team24.OpenDoors.service.NotificationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.Map;

@RequestMapping(value = "open-doors")
@CrossOrigin(origins = "*")
@Controller
public class WebSocketController {

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @Autowired
    private NotificationService notificationService;

    @RequestMapping(value="/sendMessageRest", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> message) {
        if (message.containsKey("message")) {
            if (message.containsKey("username") && message.get("username") != null && !message.get("username").isEmpty()) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.get("username"), message);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", message);
            }
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }


    @MessageMapping("/send/message")
    public NotificationDTO broadcastNotification(NotificationDTO notificationDTO) {
        System.out.println("WS: " + notificationDTO);

        if (notificationDTO.getUsername() != null && !notificationDTO.getUsername().isEmpty()) {
            if (notificationService.isEnabled(notificationDTO.getUsername(), NotificationType.fromString(notificationDTO.getType()))) {
                notificationService.add(notificationDTO);
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + notificationDTO.getUsername(),
                        notificationDTO);
            }
        } else {
            this.simpMessagingTemplate.convertAndSend("/socket-publisher", notificationDTO);
        }

        return notificationDTO;
    }

}
