package com.github.odilonjk.booking.domain;

import java.time.LocalDate;
import java.util.UUID;

/**
 * Booking represents the booking model.
 */
public interface Booking {

    String getUsername();
    String getEmail();
    LocalDate getStartDate();
    LocalDate getEndDate();
    long getDaysAmount();
    UUID getCode();

}
