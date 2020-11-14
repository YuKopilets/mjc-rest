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
            DeleteByRequestedIdServiceException.class,
            GiftCertificateNotFoundServiceException.class,
            TagNotFoundServiceException.class,
            OrderNotFoundServiceException.class
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
            InvalidRequestedIdServiceException.class,
            UserLoginIsNotValidServiceException.class,
            PageNumberNotValidServiceException.class,
            PageSizeNotValidServiceException.class,
            JsonMappingException.class,
            JsonDeserializeException.class
    })
    public ResponseEntity<Object> handleBadRequest(Exception ex, WebRequest request) {
        return handleException(ex, HttpStatus.BAD_REQUEST);
    }
}
