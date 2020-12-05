package com.github.odilonjk.booking.domain.entity;

import com.github.odilonjk.booking.domain.Booking;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
public class BookingEntity implements Booking {

    @Id
    private UUID id;
    private String username;
    private String email;
    @Column(name = "start_date")
    private LocalDate startDate;
    @Column(name = "end_date")
    private LocalDate endDate;

    public BookingEntity() {
    }

    public BookingEntity(UUID id, String username, String email, LocalDate startDate, LocalDate endDate) {
        this.id = id;
        this.username = username;
        this.email = email;
        this.startDate = startDate;
        this.endDate = endDate;
    }

    @Override
    public String getUsername() {
        return null;
    }

    @Override
    public String getEmail() {
        return null;
    }

    @Override
    public LocalDate getStartDate() {
        return null;
    }

    @Override
    public LocalDate getEndDate() {
        return null;
    }

    @Override
    public long getDaysAmount() {
        return 0;
    }

    @Override
    public UUID getCode() {
        return id;
    }
}
