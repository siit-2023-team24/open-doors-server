package com.siit.team24.OpenDoors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, reason = "You have confirmed reservation requests. You cannot delete your profile.")
public class ConfirmedReservationRequestsFound extends RuntimeException {
}
