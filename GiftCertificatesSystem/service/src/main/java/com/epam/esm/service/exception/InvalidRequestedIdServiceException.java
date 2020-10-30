package com.epam.esm.service.exception;

public class InvalidRequestedIdServiceException extends ServiceException {
    public InvalidRequestedIdServiceException() {
    }

    public InvalidRequestedIdServiceException(String message) {
        super(message);
    }

    public InvalidRequestedIdServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public InvalidRequestedIdServiceException(Throwable cause) {
        super(cause);
    }
}
