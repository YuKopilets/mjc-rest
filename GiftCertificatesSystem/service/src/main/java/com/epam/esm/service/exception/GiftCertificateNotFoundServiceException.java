package com.epam.esm.service.exception;

public class GiftCertificateNotFoundServiceException extends ServiceException {
    public GiftCertificateNotFoundServiceException() {
    }

    public GiftCertificateNotFoundServiceException(String message) {
        super(message);
    }

    public GiftCertificateNotFoundServiceException(String message, Throwable cause) {
        super(message, cause);
    }

    public GiftCertificateNotFoundServiceException(Throwable cause) {
        super(cause);
    }
}
