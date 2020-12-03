package com.github.odilonjk.booking.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.odilonjk.booking.domain.exception.BookingNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.NoSuchElementException;
import java.util.Objects;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorDetails> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetails("invalid.argument", e.getMessage()));
    }

    @ExceptionHandler(BookingNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleNotFound(BookingNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetails("not_found.booking", e.getMessage()));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class ErrorDetails {
        private final String code;
        private final String message;

        public ErrorDetails(String code, String message) {
            this.code = Objects.requireNonNull(code);
            this.message = message;
        }

        public String getCode() {
            return code;
        }

        public String getMessage() {
            return message;
        }

    }

}
