package com.epam.esm.service.exception;

public class TagNotFoundServiceException extends ServiceException {
    public TagNotFoundServiceException() {
    }

    public TagNotFoundServiceException(String message) {
        super(message);
    }

    public TagNotFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public TagNotFoundServiceException(Throwable cause) {
        super(cause);
    }
}
