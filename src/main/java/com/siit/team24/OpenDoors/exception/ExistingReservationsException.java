package com.siit.team24.OpenDoors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, reason = "Existing reservations in the future found.")
public class ExistingReservationsException extends RuntimeException {
}
