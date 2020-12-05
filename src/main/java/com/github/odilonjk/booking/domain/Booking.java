package com.github.odilonjk.booking.domain;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

/**
 * Booking represents the booking model.
 */
public interface Booking {

    String getUsername();
    String getEmail();
    LocalDate getStartDate();
    LocalDate getEndDate();
    UUID getCode();

    default long getDaysAmount() {
        return ChronoUnit.DAYS.between(getStartDate(), getEndDate());
    }

}
