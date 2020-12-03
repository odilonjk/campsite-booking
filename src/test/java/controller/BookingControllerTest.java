package controller;

import com.github.odilonjk.booking.controller.BookingController;
import org.junit.jupiter.api.DisplayName;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BookingControllerTest {

    BookingController controller = new BookingController();

    @DisplayName("Should return Not Found error when a booking was not found")
    public void testBookingNotFound() {
//        ResponseEntity<Booking> response = controller.getBooking("ABC123");
//
//        assertEquals(HttpStatus.NOT_FOUND.value(), response.getStatusCode(), "Received the wrong status code");
//        assertEquals(response.getBody().ge);
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
