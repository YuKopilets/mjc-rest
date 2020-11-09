package com.epam.esm.error;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

@RestControllerAdvice
@Order
public class CommonControllerExceptionHandler extends ControllerExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
