package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.service.BookingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class BookingController {

    @Autowired
    private BookingService bookingService;

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.findBooking(id));
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
