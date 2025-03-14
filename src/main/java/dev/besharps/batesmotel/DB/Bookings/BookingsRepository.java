package dev.besharps.batesmotel.DB.Bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.PathVariable;

import java.awt.print.Book;
import java.util.Optional;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
/*QUERIES ARE MADE IN THE INTERFACE
  QUERIES ARE ONLY NEEDED FOR CUSTOM QUERIES
  THIS INTERFACE HAS DEFAULT IMPLEMENTATIONS OF BASIC CRUD OPS
  OTHER THAN THAT NOTHING ELSE NEEDS TO GO IN THIS FILE*/
}
