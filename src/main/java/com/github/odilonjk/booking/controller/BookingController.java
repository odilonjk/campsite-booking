package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Set;
import java.util.UUID;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.findBooking(id));
    }

    @GetMapping("/bookings/available-dates")
    public ResponseEntity<Set<LocalDate>> getAvailableBookingDates(@RequestParam(required = false)
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                   LocalDate startDate,
                                                                   @RequestParam(required = false)
                                                                   @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
                                                                   LocalDate endDate) {
        if (startDate != null && endDate == null) {
            throw new IllegalArgumentException("invalid.end_date");
        }
        if (endDate != null && startDate == null) {
            throw new IllegalArgumentException("invalid.start_date");
        }
        if (startDate != null && endDate != null && startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("invalid.date_range");
        }
        return ResponseEntity.ok(bookingService.findAvailableBookingDates(startDate, endDate));
    }

    @PostMapping("/bookings")
    public ResponseEntity<UUID> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }

    @DeleteMapping("/bookings/{id}")
    public ResponseEntity<Void> removeBooking(@PathVariable UUID id) {
        bookingService.removeBooking(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("bookings/{id}")
    public ResponseEntity<Void> updateBooking(@PathVariable UUID id, @Valid @RequestBody BookingRequest request) {
        bookingService.updateBooking(id, request);
        return ResponseEntity.noContent().build();
    }
}
