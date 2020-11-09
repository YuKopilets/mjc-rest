package com.epam.esm.error;

import com.epam.esm.exception.JsonDeserializeException;
import com.epam.esm.service.exception.*;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecialControllerExceptionHandler extends ControllerExceptionHandler {
    @ExceptionHandler({
            DeleteByRequestedIdServiceException.class,
            GiftCertificateNotFoundServiceException.class,
            TagNotFoundServiceException.class
    })
    public ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            InvalidRequestedIdServiceException.class,
            LoginIsNotValidServiceException.class,
            JsonMappingException.class,
            JsonDeserializeException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }
}
