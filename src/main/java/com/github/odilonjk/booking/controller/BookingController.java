package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.service.BookingService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
public class BookingController {

    private BookingService bookingService;

    public BookingController(BookingService bookingService) {
        this.bookingService = bookingService;
    }

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable UUID id) {
        return ResponseEntity.ok(bookingService.findBooking(id));
    }

    @PostMapping("/bookings")
    public ResponseEntity<UUID> createBooking(@Valid @RequestBody BookingRequest request) {
        return ResponseEntity.ok(bookingService.createBooking(request));
    }
}
