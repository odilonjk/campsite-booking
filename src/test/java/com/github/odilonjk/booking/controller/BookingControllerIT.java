package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.domain.repository.BookingRepository;
import com.github.odilonjk.booking.exception.BookingNotFoundException;
import com.github.odilonjk.booking.exception.OverlappedBookingException;
import com.github.odilonjk.booking.service.BookingService;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.ResponseEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class BookingControllerIT {

    @Autowired
    BookingRepository repository;

    @Autowired
    BookingController controller;

    @AfterEach
    void clean() {
        repository.deleteAll();
    }

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

    @Test
    @DisplayName("Should not permit create more than one booking on same time")
    void testOverlapBooking() {
        // CREATING FIRST BOOKING
        var startDateFirstBooking = LocalDate.now().plus(5, ChronoUnit.DAYS);
        var endDateFirstBooking = startDateFirstBooking.plus(3, ChronoUnit.DAYS);
        var username = "Foo Bar";
        var email = "foo@bar";
        var request = new BookingRequest(username, email, startDateFirstBooking, endDateFirstBooking);
        var createResponse = controller.createBooking(request);
        var code = UUID.fromString(createResponse.getBody().toString());

        // SEARCHING FIRST BOOKING
        ResponseEntity<Booking> findResponse = controller.getBooking(code);
        assertNotNull(findResponse.getBody(), "Should have found the first booking");

        // CREATING SECOND BOOKING
        var startDateSecondBooking = LocalDate.now().plus(7, ChronoUnit.DAYS);
        var endDateSecondBooking = startDateSecondBooking;
        var secondBookingRequest = new BookingRequest(username, email, startDateSecondBooking, endDateSecondBooking);

        // ASSERT IT WAS NOT POSSIBLE TO CREATE SECOND BOOKING BECAUSE OF THE DATE RANGE
        assertThrows(OverlappedBookingException.class, () -> {
            controller.createBooking(secondBookingRequest);
        });
    }

    @Test
    @DisplayName("Should not permit update when overlapping date range")
    void testUpdateOverlappingBooking() {
        // CREATING FIRST BOOKING
        var startDateFirstBooking = LocalDate.now().plus(5, ChronoUnit.DAYS);
        var endDateFirstBooking = startDateFirstBooking.plus(3, ChronoUnit.DAYS);
        var username = "Foo Bar";
        var email = "foo@bar";
        var request = new BookingRequest(username, email, startDateFirstBooking, endDateFirstBooking);
        var createResponse = controller.createBooking(request);
        var code = UUID.fromString(createResponse.getBody().toString());

        // SEARCHING FIRST BOOKING
        ResponseEntity<Booking> findResponse = controller.getBooking(code);
        assertNotNull(findResponse.getBody(), "Should have found the first booking");

        // CREATING SECOND BOOKING
        var startDateSecondBooking = LocalDate.now().plus(8, ChronoUnit.DAYS);
        var endDateSecondBooking = startDateSecondBooking;
        var secondBookingRequest = new BookingRequest(username, email, startDateSecondBooking, endDateSecondBooking);
        var secondCreateResponse = controller.createBooking(secondBookingRequest);
        var secondCode = UUID.fromString(secondCreateResponse.getBody().toString());

        // SEARCHING SECOND BOOKING
        ResponseEntity<Booking> secondBookingResponse = controller.getBooking(secondCode);
        var secondBooking = secondBookingResponse.getBody();
        assertNotNull(secondBooking, "Should have found the second booking");

        // SETTING NEW DATE RANGE FOR SECOND BOOKING
        var newStartDateSecondBooking = startDateSecondBooking.minus(1, ChronoUnit.DAYS);
        var updateBookingRequest = new BookingRequest(username, email, newStartDateSecondBooking, endDateSecondBooking);

        // ASSERT THAT IT'S NOT POSSIBLE TO OVERLAP BOOKINGS ON UPDATE
        assertThrows(OverlappedBookingException.class, () -> {
           controller.updateBooking(secondCode, updateBookingRequest);
        });
    }

}
