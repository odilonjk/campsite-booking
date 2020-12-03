package com.github.odilonjk.booking.domain;

import java.time.LocalDate;

/**
 * Booking represents the booking model.
 */
public interface Booking {

    String getUsername();
    String getEmail();
    LocalDate getStartDate();
    LocalDate getEndDate();
    long getDaysAmount();
    String getCode();

}
