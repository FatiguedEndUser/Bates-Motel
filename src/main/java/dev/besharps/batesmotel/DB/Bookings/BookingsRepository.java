package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.DB.Customer.Customer;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.web.bind.annotation.RequestBody;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
    @Query("select Bookings b, Customer c from Bookings where c.customerId = :id")
    Bookings findByCustomer(Customer customer, Integer id);


}
