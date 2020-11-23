package com.epam.esm.error;

import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

/**
 * The type Common controller with low priority {@code @Order} for handling all
 * not defined exceptions.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see ControllerExceptionHandler
 */
@RestControllerAdvice
@Order
public class CommonControllerExceptionHandler extends ControllerExceptionHandler {
    /**
     * Handle all not handled errors and define them like <i>internal server
     * error</i> status.
     *
     * @param ex      the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({Exception.class})
    public ResponseEntity<Object> handleInternalServerError(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
