package com.github.odilonjk.booking.service;

import com.github.odilonjk.booking.controller.BookingRequest;
import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.domain.entity.BookingEntity;
import com.github.odilonjk.booking.domain.exception.BookingNotFoundException;
import com.github.odilonjk.booking.domain.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    private BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public Booking findBooking(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found for id code: " + id.toString()));
    }

    public UUID createBooking(Booking request) {
        var newBooking = new BookingEntity(UUID.randomUUID(),
                request.getUsername(),
                request.getEmail(),
                request.getStartDate(),
                request.getEndDate());
        var booking = repository.save(newBooking);
        return booking.getCode();
    }
}
