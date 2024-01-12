package com.siit.team24.OpenDoors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.METHOD_NOT_ALLOWED, reason = "Sorry. The deadline for cancelling a reservation at this accommodation has passed.")

public class CancelRequestException extends RuntimeException {
}
