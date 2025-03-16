package dev.besharps.batesmotel.DB.Customer;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

    @Query("select c from Customer c where c.firstName == :firstName ")
    Customer findByFirstName(@Param("firstName") String firstName);

    @Query("select c from Customer c where c.lastName == :lastName ")
    Customer findByLastName(@Param("lastName") String lastName);

    //TODO: Implement
    @Query
    Customer findByPhoneNumber(String phoneNumber);
}
