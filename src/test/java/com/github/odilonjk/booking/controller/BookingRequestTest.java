package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.exception.InvalidBookingRequestException;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class BookingRequestTest {

    @ParameterizedTest(name = "Test booking request date with {1}")
    @MethodSource("dateArguments")
    public void testBookingRequestDate(String message, String expectedError, LocalDate startDate, LocalDate endDate) {
        // GIVEN
        var name = "Tester";
        var email = "a@b.c";

        // THEN
        var exception = assertThrows(InvalidBookingRequestException.class, () -> {
            new BookingRequest(name, email, startDate, endDate);
        });
        assertEquals(expectedError, exception.getMessage(), message);
    }

    static Stream<Arguments> dateArguments () {
        return Stream.of(
                Arguments.of(
                        "Should not allow a booking request with more than 3 days duration",
                        "invalid.duration",
                        LocalDate.now().plus(4, ChronoUnit.DAYS),
                        LocalDate.now().plus(8, ChronoUnit.DAYS)
                ),
                Arguments.of(
                        "Should not allow a booking request with less than 1 day ahead of arrival",
                        "invalid.start_date",
                        LocalDate.now(),
                        LocalDate.now().plus(3, ChronoUnit.DAYS)
                ),
                Arguments.of(
                        "Should not allow a booking request with more than 1 month in advance",
                        "invalid.start_month",
                        LocalDate.now().plus(2, ChronoUnit.MONTHS),
                        LocalDate.now().plus(3, ChronoUnit.DAYS).plus(2, ChronoUnit.MONTHS)
                ),
                Arguments.of(
                        "Should not allow a booking request end date before start date",
                        "invalid.end_date",
                        LocalDate.now().plus(2, ChronoUnit.DAYS),
                        LocalDate.now().plus(1, ChronoUnit.DAYS)
                )
        );
    }

}
