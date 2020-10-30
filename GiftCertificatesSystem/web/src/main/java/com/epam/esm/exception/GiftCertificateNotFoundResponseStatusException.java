package com.epam.esm.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class GiftCertificateNotFoundResponseStatusException extends ResponseStatusException {
    public GiftCertificateNotFoundResponseStatusException(Throwable cause) {
        super(HttpStatus.NOT_FOUND, "GiftCertificate not found", cause);
    }

    public GiftCertificateNotFoundResponseStatusException(HttpStatus status) {
        super(status);
    }

    public GiftCertificateNotFoundResponseStatusException(HttpStatus status, String reason) {
        super(status, reason);
    }

    public GiftCertificateNotFoundResponseStatusException(HttpStatus status, String reason, Throwable cause) {
        super(status, reason, cause);
    }
}
