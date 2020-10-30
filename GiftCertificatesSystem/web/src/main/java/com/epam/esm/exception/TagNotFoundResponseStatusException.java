package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class TagNotFoundResponseStatusException extends ResponseStatusException {
    public TagNotFoundResponseStatusException(Throwable cause) {
        super(HttpStatus.NOT_FOUND, "Tag not found", cause);
    }

    public TagNotFoundResponseStatusException(HttpStatus status) {
        super(status);
    }

    public TagNotFoundResponseStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public TagNotFoundResponseStatusException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
