package com.github.odilonjk.booking.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.github.odilonjk.booking.domain.Booking;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Objects;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequest implements Booking {

    private final String username;
    private final String email;
    private final LocalDate startDate;
    private final LocalDate endDate;

    public BookingRequest(String username, String email, LocalDate startDate, LocalDate endDate) {
        this.username = Objects.requireNonNull(username);
        this.email = Objects.requireNonNull(email);
        this.startDate = Objects.requireNonNull(startDate);
        this.endDate = Objects.requireNonNull(endDate);
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public long getDaysAmount() {
        return ChronoUnit.DAYS.between(startDate, endDate);
    }

    @Override
    public String getCode() {
        return null;
    }
}
