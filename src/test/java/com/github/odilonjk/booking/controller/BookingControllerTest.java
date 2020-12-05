package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.exception.BookingNotFoundException;
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
        // CREATING BOOKING
        var startDate = LocalDate.now().plus(5, ChronoUnit.DAYS);
        var endDate = startDate.plus(3, ChronoUnit.DAYS);
        var username = "Foo Bar";
        var email = "foo@bar";
        var request = new BookingRequest(username, email, startDate, endDate);
        var createResponse = controller.createBooking(request);
        var code = UUID.fromString(createResponse.getBody().toString());

        // SEARCHING BOOKING
        ResponseEntity<Booking> findResponse = controller.getBooking(code);

        // ASSERTS
        var booking = findResponse.getBody();
        assertNotNull(booking, "Should have found the booking");
        assertEquals(3, booking.getDaysAmount(), "Should have considered the correct amount of booking days");
        assertEquals(email, booking.getEmail(), "Should have the same email");
        assertEquals(username, booking.getUsername(), "Should have the same user name");
        assertEquals(code, booking.getCode(), "Should have the same code");
        assertEquals(startDate, booking.getStartDate(), "Should have the same start date");
        assertEquals(endDate, booking.getEndDate(), "Should have the same end date");
    }

    @Test
    @DisplayName("Should update successfully the required booking")
    void testUpdateBookingSuccessfully() {
        // CREATING BOOKING
        var startDate = LocalDate.now().plus(5, ChronoUnit.DAYS);
        var endDate = startDate.plus(3, ChronoUnit.DAYS);
        var username = "Foo Bar";
        var email = "foo@bar";
        var request = new BookingRequest(username, email, startDate, endDate);
        var createResponse = controller.createBooking(request);
        var code = UUID.fromString(createResponse.getBody().toString());

        // SEARCHING BOOKING
        ResponseEntity<Booking> findResponse = controller.getBooking(code);

        // ASSERTS
        var booking = findResponse.getBody();
        assertNotNull(booking, "Should have found the booking");
        assertEquals(3, booking.getDaysAmount(), "Should have considered the correct amount of booking days");
        assertEquals(email, booking.getEmail(), "Should have the same email");
        assertEquals(username, booking.getUsername(), "Should have the same user name");
        assertEquals(code, booking.getCode(), "Should have the same code");
        assertEquals(startDate, booking.getStartDate(), "Should have the same start date");
        assertEquals(endDate, booking.getEndDate(), "Should have the same end date");

        // UPDATE BOOKING
        var newEndDate = endDate.minus(2, ChronoUnit.DAYS);
        var newBooking = new BookingRequest(username, email, startDate, newEndDate);
        var updateResponde = controller.updateBooking(code, newBooking);
        assertTrue(updateResponde.getStatusCode().is2xxSuccessful(), "Should have updated the booking");

        // SEARCHING UPDATED BOOKING
        ResponseEntity<Booking> findUpdatedResponse = controller.getBooking(code);
        assertEquals(newEndDate, findUpdatedResponse.getBody().getEndDate(), "Should have the same end date");
    }

    @Test
    @DisplayName("Should have successfully canceled the required booking")
    void testRemoveBookingSuccessfully() {
        // CREATING BOOKING
        var startDate = LocalDate.now().plus(5, ChronoUnit.DAYS);
        var endDate = startDate.plus(3, ChronoUnit.DAYS);
        var username = "Foo Bar";
        var email = "foo@bar";
        var request = new BookingRequest(username, email, startDate, endDate);
        var createResponse = controller.createBooking(request);
        var code = UUID.fromString(createResponse.getBody().toString());

        // VALIDATING IT EXISTS
        ResponseEntity<Booking> findResponse = controller.getBooking(code);
        assertTrue(findResponse.getStatusCode().is2xxSuccessful(), "Should have found the booking");

        // CANCELING IT
        ResponseEntity<Void> cancelResponse = controller.removeBooking(code);
        assertTrue(cancelResponse.getStatusCode().is2xxSuccessful(), "Should have remover the booking");

        // VALIDATING IT DOES NOT EXIST ANYMORE
        assertThrows(BookingNotFoundException.class, () -> {
            controller.getBooking(code);
        });
    }

}
