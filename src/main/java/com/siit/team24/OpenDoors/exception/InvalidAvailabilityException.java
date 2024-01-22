package com.siit.team24.OpenDoors.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.BAD_REQUEST, reason = "The availability of the accommodation is invalid.")
public class InvalidAvailabilityException extends RuntimeException {}