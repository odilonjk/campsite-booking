package com.github.odilonjk.booking.controller;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.github.odilonjk.booking.exception.BookingNotFoundException;
import com.github.odilonjk.booking.exception.InvalidBookingRequestException;
import com.github.odilonjk.booking.exception.OverlappedBookingException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.Objects;

@ControllerAdvice
public class ResponseExceptionHandler {

    @ExceptionHandler(IllegalArgumentException.class)
    protected ResponseEntity<ErrorDetails> handleBadRequest(IllegalArgumentException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetails("invalid.argument"));
    }

    @ExceptionHandler(BookingNotFoundException.class)
    protected ResponseEntity<ErrorDetails> handleNotFound(BookingNotFoundException e) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(new ErrorDetails("not_found.booking"));
    }

    @ExceptionHandler(InvalidBookingRequestException.class)
    public ResponseEntity<ErrorDetails> handleInvalidBookingDuration(Exception e) {
        return ResponseEntity
            .status(HttpStatus.BAD_REQUEST)
            .body(new ErrorDetails(e.getCause().getCause().getMessage()));
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorDetails> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetails(e.getAllErrors().get(0).getDefaultMessage()));
    }

    @ExceptionHandler(OverlappedBookingException.class)
    public ResponseEntity<ErrorDetails> handleOverlappedBooking(OverlappedBookingException e) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(new ErrorDetails("invalid.overlapped_booking"));
    }

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private class ErrorDetails {
        private final String code;

        public ErrorDetails(String code) {
            this.code = Objects.requireNonNull(code);
        }

        public String getCode() {
            return code;
        }

    }

}
