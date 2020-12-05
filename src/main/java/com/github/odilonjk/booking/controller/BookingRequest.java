package com.github.odilonjk.booking.controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.exception.InvalidBookingRequestException;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

@JsonIgnoreProperties(ignoreUnknown = true)
public class BookingRequest implements Booking {

    @NotBlank(message = "invalid.username")
    private final String username;

    @NotBlank(message = "invalid.email")
    @Email(message = "invalid.email")
    private final String email;

    @NotNull(message = "invalid.start_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate startDate;

    @NotNull(message = "invalid.end_date")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    private final LocalDate endDate;

    public BookingRequest(String username, String email, LocalDate startDate, LocalDate endDate) {

        var today = LocalDate.now();
        if (today.isAfter(startDate) || today.isEqual(startDate)) {
            throw new InvalidBookingRequestException("invalid.start_date");
        }

        if (today.getMonth().compareTo(startDate.getMonth()) > 0) {
            throw new InvalidBookingRequestException("invalid.start_month");
        }

        if (endDate.isBefore(startDate)) {
            throw new InvalidBookingRequestException("invalid.end_date");
        }

        if (ChronoUnit.DAYS.between(startDate, endDate) > 3) {
            throw new InvalidBookingRequestException("invalid.duration");
        }

        this.username = username;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
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
    public UUID getCode() {
        return null;
    }
}
