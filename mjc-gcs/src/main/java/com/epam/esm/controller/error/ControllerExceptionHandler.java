package com.epam.esm.controller.error;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

/**
 * The abstract type Controller exception handler for common handle operations
 * and logging.
 *
 * @author Yuriy Kopilets
 * @version 1.0
 */
@Slf4j
public abstract class ControllerExceptionHandler {
    private static final String ERROR_MESSAGE = "message";
    private static final String ERROR_TIMESTAMP = "timestamp";
    private static final String ERROR_CODE = "code";

    protected ResponseEntity<Object> handleException(Exception ex, HttpStatus httpStatus) {
        Map<String, Object> body = new HashMap<>();
        body.put(ERROR_MESSAGE, ex.getMessage());
        body.put(ERROR_TIMESTAMP, LocalDateTime.now());
        body.put(ERROR_CODE, httpStatus.value());
        log.error(ex.getMessage(), ex);
        return new ResponseEntity<>(body, httpStatus);
    }
}
