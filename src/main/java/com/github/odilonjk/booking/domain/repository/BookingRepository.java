package com.github.odilonjk.booking.domain.repository;

import com.github.odilonjk.booking.domain.entity.BookingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import javax.persistence.Tuple;
import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

@Repository
public interface BookingRepository extends JpaRepository<BookingEntity, UUID> {

    @Query("select case when count(b)> 0 then true else false end " +
            "from BookingEntity b " +
            "where (b.startDate < :endDate " +
            "and b.endDate >= :endDate " +
            "and b.id != :id) " +
            "or (b.startDate <= :startDate " +
            "and b.endDate > :startDate " +
            "and b.id != :id)")
    boolean existsBookingToBeOverlapped(@Param("startDate") LocalDate startDate,
                                        @Param("endDate") LocalDate endDate,
                                        @Param("id") UUID id);

    @Query("select startDate, endDate from BookingEntity where startDate >= :start and endDate <= :end")
    List<Tuple> findBookingDatesInDateRange(@Param("start") LocalDate start, @Param("end") LocalDate end);

}
