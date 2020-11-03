package com.epam.esm.error;

import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.InvalidRequestedIdServiceException;
import com.epam.esm.service.exception.ServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@RestControllerAdvice
public class ControllerExceptionHandler {
    @ExceptionHandler(InvalidRequestedIdServiceException.class)
    public ResponseEntity<Object> handleInvalidRequestedIdServiceException(InvalidRequestedIdServiceException ex, WebRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({GiftCertificateNotFoundServiceException.class, TagNotFoundServiceException.class})
    public ResponseEntity<Object> handleServiceException(ServiceException ex, WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    private ResponseEntity<Object> handleException(Exception ex, HttpStatus httpStatus) {
        Map<String, Object> body = new HashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", ex.getMessage());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(body, httpStatus);
    }
}
