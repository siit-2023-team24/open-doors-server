package com.siit.team24.OpenDoors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, reason = "There are active (pending or confirmed) reservations for this accommodation. Editing is not allowed.")
public class ActiveReservationRequestsFoundException extends RuntimeException {
}
