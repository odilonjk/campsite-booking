package com.github.odilonjk.booking.service;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.domain.exception.BookingNotFoundException;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class BookingService {

    public Booking findBooking(String id) {
        throw new BookingNotFoundException("Booking not found for id code: " + id);
    }
}
