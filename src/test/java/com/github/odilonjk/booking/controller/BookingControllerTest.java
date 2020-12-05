package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.domain.exception.BookingNotFoundException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingControllerTest {

    @Autowired
    BookingController controller;

    @Test
    @DisplayName("Should throw exception when a booking was not found")
    void testFindBookingNotFound() {
        assertThrows(BookingNotFoundException.class, () -> {
            controller.getBooking(UUID.randomUUID());
        });
    }

    @Test
    @DisplayName("Should have found successfully the required booking")
    void testFindBookingSuccessfully() {
        // GIVEN
        var startDate = LocalDate.now().plus(5, ChronoUnit.DAYS);
        var endDate = startDate.plus(3, ChronoUnit.DAYS);
        var request = new BookingRequest("Fulano", "aff@o", startDate, endDate);
        var response = controller.createBooking(request);
        var code = UUID.fromString(response.getBody().toString());

        // WHEN
        ResponseEntity<Booking> booking = controller.getBooking(code);

        // THEN
        assertNotNull(booking);
    }

//    @DisplayName("Should return Bad Request when the username is not filled")
//    public void testUsername() {
//        // GIVEN
//        final var username = "";
//        final var email = "tester@booking.camp";
//        final var startDate = LocalDate.now();
//        final var endDate = startDate.plus(1, ChronoUnit.DAYS);
//        var bookingRequest = new BookingRequest(username, email, startDate, endDate);
//
//        controller.createBooking(bookingRequest);
//        // THEN
//
//
//
//
//    }

//    @ParameterizedTest(name = "Should book a campsite for {days} days: {result}")
//    public void testBookMaxDays(int days, boolean result) {
//        // GIVEN
//        final var username = "Foo 'Tester' Bar";
//        final var email = "tester@booking.camp";
//        final var startDate = LocalDate.now();
//        final var endDate = startDate.plus(days, ChronoUnit.DAYS);
//        var bookingRequest = new BookingRequest(username, email, startDate, endDate);
//
//        // WHEN
//        var code = controller.createBooking(bookingRequest);
//
//        // THEN
//        Assertions.assert
//
//    }

}
