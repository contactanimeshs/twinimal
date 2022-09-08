package com.contactanimeshs.twinimal.exception;

import com.contactanimeshs.twinimal.constants.ErrorCodes;
import com.contactanimeshs.twinimal.model.error.ErrorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Animesh (@contactanimeshs)
 */
@ControllerAdvice
@Slf4j
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(value = {IllegalArgumentException.class, IllegalStateException.class})
    protected ResponseEntity<Object> handleConflict(RuntimeException ex, WebRequest request) {
        String bodyOfResponse = "Something went wrong";
        return handleExceptionInternal(ex, bodyOfResponse, new HttpHeaders(), HttpStatus.CONFLICT, request);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> exceptionHandler(HttpServletRequest request, Exception e) {
        log.error("Error occurred while accessing: " + request.getRequestURL());
        log.error("Exception: " + e.getMessage());
        ErrorResponse errorResponse = new ErrorResponse(ErrorCodes.GENERIC_ERROR);
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}