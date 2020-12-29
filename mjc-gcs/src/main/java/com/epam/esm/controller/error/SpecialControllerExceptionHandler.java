package com.epam.esm.controller.error;

import com.epam.esm.service.exception.GiftCertificateNotFoundServiceException;
import com.epam.esm.service.exception.OrderNotFoundServiceException;
import com.epam.esm.service.exception.TagNotFoundServiceException;
import com.epam.esm.service.exception.UserNotFoundServiceException;
import com.fasterxml.jackson.databind.JsonMappingException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import javax.validation.ConstraintViolationException;

/**
 * The type Special controller with highest priority {@code @Order}
 * for defining exceptions and handling them.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 * @see ControllerExceptionHandler
 */
@RestControllerAdvice
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SpecialControllerExceptionHandler extends ControllerExceptionHandler {
    /**
     * {@code @ExceptionHandler} defines exceptions for handling like <i>not
     * found</i> status
     *
     * @param ex      the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({
            GiftCertificateNotFoundServiceException.class,
            TagNotFoundServiceException.class,
            OrderNotFoundServiceException.class,
            UserNotFoundServiceException.class
    })
    public ResponseEntity<Object> handleNotFound(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.NOT_FOUND);
    }

    /**
     * {@code @ExceptionHandler} defines exceptions for handling like <i>bad
     * request</i> status
     *
     * @param ex      the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({
            MethodArgumentNotValidException.class,
            ConstraintViolationException.class,
            JsonMappingException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }

    /**
     * {@code @ExceptionHandler} defines exceptions for handling like
     * <i>forbidden</i> status
     *
     * @param ex      the exception
     * @param request the request
     * @return the response entity
     */
    @ExceptionHandler({
            AccessDeniedException.class
    })
    public ResponseEntity<Object> handleForbidden(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.FORBIDDEN);
    }
}
