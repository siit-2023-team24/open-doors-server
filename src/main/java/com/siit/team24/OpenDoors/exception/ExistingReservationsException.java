package com.siit.team24.OpenDoors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, reason = "Confirmed reservations found for this accommodation. Deleting it is not allowed.")
public class ExistingReservationsException extends RuntimeException {
}
