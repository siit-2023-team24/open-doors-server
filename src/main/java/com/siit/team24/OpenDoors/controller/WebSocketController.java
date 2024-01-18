package com.siit.team24.OpenDoors.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
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

//    @CrossOrigin(origins = "http://localhost:4200")
    @RequestMapping(value="/sendMessageRest", method = RequestMethod.POST)
    public ResponseEntity<?> sendMessage(@RequestBody Map<String, String> message) {
        System.err.println("REST: " + message);

        if (message.containsKey("message")) {
            if (message.containsKey("userId") && message.get("userId") != null && !message.get("userId").isEmpty()) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + message.get("userId"), message);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", message);
            }
            return new ResponseEntity<>(message, new HttpHeaders(), HttpStatus.OK);
        }

        return new ResponseEntity<>(new HttpHeaders(), HttpStatus.BAD_REQUEST);
    }

    @MessageMapping("/send/message")
    public Map<String, String> broadcastNotification(String message) {
        System.err.println("WS1: " + message);

        Map<String, String> messageConverted = parseMessage(message);

        System.err.println("WS2: " + message);


        if (messageConverted != null) {
            if (messageConverted.containsKey("userId") && messageConverted.get("userId") != null
                    && !messageConverted.get("userId").isEmpty()) {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher/" + messageConverted.get("userId"),
                        messageConverted);
            } else {
                this.simpMessagingTemplate.convertAndSend("/socket-publisher", messageConverted);
            }
        }
        return messageConverted;
    }

    @SuppressWarnings("unchecked")
    private Map<String, String> parseMessage(String message) {
        ObjectMapper mapper = new ObjectMapper();
        Map<String, String> retVal;

        try {
            retVal = mapper.readValue(message, Map.class);
        } catch (IOException e) {
            retVal = null;
        }
        return retVal;
    }
}
