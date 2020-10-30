package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class InvalidRequestedIdResponseStatusException extends ResponseStatusException {
    public InvalidRequestedIdResponseStatusException(Throwable cause) {
        super(HttpStatus.BAD_REQUEST, "Invalid id value. Id must be positive number", cause);
    }

    public InvalidRequestedIdResponseStatusException(HttpStatus status) {
        super(status);
    }

    public InvalidRequestedIdResponseStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public InvalidRequestedIdResponseStatusException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
