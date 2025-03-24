package dev.besharps.batesmotel.DB.Bookings;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
    //@Query("select Bookings b, Customer c from Bookings where c.customerId = :id")
    //Bookings findByCustomer(Customer customer, Integer id);


}
