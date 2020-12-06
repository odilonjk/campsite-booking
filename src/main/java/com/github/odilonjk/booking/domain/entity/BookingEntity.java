package com.github.odilonjk.booking.domain.entity;

import com.github.odilonjk.booking.domain.Booking;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDate;
import java.util.UUID;

@Entity
@DynamicUpdate
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

    public BookingEntity(UUID id, Booking booking) {
        this.id = id;
        this.username = booking.getUsername();
        this.email = booking.getEmail();
        this.startDate = booking.getStartDate();
        this.endDate = booking.getEndDate();
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public LocalDate getStartDate() {
        return startDate;
    }

    @Override
    public LocalDate getEndDate() {
        return endDate;
    }

    @Override
    public UUID getCode() {
        return id;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }
}
