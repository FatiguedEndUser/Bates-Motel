package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.DB.Services.Services;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
    @Query("SELECT s FROM Bookings b JOIN b.services s WHERE b.bookingId = :id")
    List<Services> findServicesByBookingId(@Param("id") Integer bookingId);
}

