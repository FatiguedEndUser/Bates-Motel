package dev.besharps.batesmotel.DB.Customer;

import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.List;

public interface CustomerRepository extends JpaRepository<Customer, Integer> {

/*    @Query("select c from Customer c where c.firstName == :firstName ")
    Customer findByFirstName(@Param("firstName") String firstName);

    @Query("select c from Customer c where c.lastName == :lastName ")
    Customer findByLastName(@Param("lastName") String lastName);

    //TODO: Implement
    @Query
    Customer findByPhoneNumber(String phoneNumber);

    //TODO: update method is a little more difficult than i anticipated
    //Maybe multiple update methods that then can be called in one method too update all
    @Query("update Customer set firstName = :firstName, set lastName = :lastName")
    Customer updateCustomer(@Valid @RequestBody String firstName, String lastName);*/
}
