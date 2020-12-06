package com.github.odilonjk.booking.domain.repository;

import com.github.odilonjk.booking.domain.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    @Query("select case when count(b)> 0 then true else false end from BookingEntity b where b.endDate > :date and b.id != :id")
    boolean existsBookingToBeOverlapped(LocalDate date, UUID id);

}
