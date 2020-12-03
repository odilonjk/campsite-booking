package com.github.odilonjk.booking.controller;

import com.github.odilonjk.booking.domain.Booking;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import com.github.odilonjk.booking.service.BookingService;

@RestController
public class BookingController {

    @Autowired
    BookingService bookingService;

    @GetMapping("/bookings/{id}")
    public ResponseEntity<Booking> getBooking(@PathVariable String id) {
        return ResponseEntity.ok(bookingService.findBooking(id));
    }

}
