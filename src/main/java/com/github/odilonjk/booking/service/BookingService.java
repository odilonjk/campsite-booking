package com.github.odilonjk.booking.service;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.domain.entity.BookingEntity;
import com.github.odilonjk.booking.exception.BookingNotFoundException;
import com.github.odilonjk.booking.domain.repository.BookingRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class BookingService {

    private BookingRepository repository;

    public BookingService(BookingRepository repository) {
        this.repository = repository;
    }

    public BookingEntity findBooking(UUID id) {
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

    public void removeBooking(UUID id) {
        repository.deleteById(id);
    }

    public void updateBooking(UUID id, Booking bookingValues) {
        var booking = this.findBooking(id);
        booking.setUsername(bookingValues.getUsername());
        booking.setEmail(bookingValues.getEmail());
        booking.setStartDate(bookingValues.getStartDate());
        booking.setEndDate(bookingValues.getEndDate());
        repository.save(booking);
    }
}
