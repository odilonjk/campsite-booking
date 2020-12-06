package com.github.odilonjk.booking.service;

import com.github.odilonjk.booking.domain.Booking;
import com.github.odilonjk.booking.domain.entity.BookingEntity;
import com.github.odilonjk.booking.domain.repository.BookingRepository;
import com.github.odilonjk.booking.exception.BookingNotFoundException;
import com.github.odilonjk.booking.exception.OverlappedBookingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.*;

@Service
public class BookingService {

    @Autowired
    private BookingRepository repository;

    public BookingEntity findBooking(UUID id) {
        return repository.findById(id)
                .orElseThrow(() -> new BookingNotFoundException("Booking not found for id code: " + id.toString()));
    }

    @Transactional
    public UUID createBooking(Booking bookingRequest) {
        var newId = UUID.randomUUID();
        if (repository.existsBookingToBeOverlapped(bookingRequest.getStartDate(), newId)) {
            throw new OverlappedBookingException("There's already another booking using this date: " + bookingRequest.getStartDate());
        }
        var newBooking = new BookingEntity(newId, bookingRequest);
        var booking = repository.save(newBooking);
        return booking.getCode();
    }

    public void removeBooking(UUID id) {
        repository.deleteById(id);
    }

    @Transactional
    public void updateBooking(UUID id, Booking updatedValues) {
        if (!repository.existsById(id)) {
            throw new BookingNotFoundException("Cannot update a booking that does not exist");
        }
        if (repository.existsBookingToBeOverlapped(updatedValues.getStartDate(), id)) {
            throw new OverlappedBookingException("There's already another booking using this date: " + updatedValues.getStartDate());
        }
        var bookingEntity = new BookingEntity(id, updatedValues);
        repository.save(bookingEntity);
    }

    public SortedSet<LocalDate> findAvailableBookingDates(LocalDate startDate, LocalDate endDate) {
        var start = startDate != null ? startDate : LocalDate.now();
        var end = endDate != null ? endDate : start.plus(1, ChronoUnit.MONTHS);
        SortedSet<LocalDate> availableDates = new TreeSet<>();
        Set<LocalDate> unavailableDates = new HashSet<>();
        List<Tuple> bookings = repository.findBookingDatesInDateRange(start, end);
        bookings.stream()
                .forEach(dates -> {
                    var bookingStart = (LocalDate) dates.get(0);
                    var bookingEnd = (LocalDate) dates.get(1);
                    for (LocalDate date = bookingStart; !date.isEqual(bookingEnd); date = date.plus(1, ChronoUnit.DAYS)) {
                        unavailableDates.add(date);
                    }
                });
        for (LocalDate date = start; !date.isAfter(end); date = date.plus(1, ChronoUnit.DAYS)) {
            if (!unavailableDates.contains(date)) {
                availableDates.add(date);
            }
        }
        return availableDates;
    }
}
