package dev.besharps.batesmotel.DB.Customer;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE c.firstName = :firstName")
    List<Customer> findByFirstName(@Param("firstName") String firstName);


    @Query("SELECT c FROM Customer c WHERE c.lastName = :lastName ")
    List<Customer> findByLastName(@Param("lastName") String lastName);

    @Query("SELECT c FROM Customer c WHERE c.phoneNumber = :number ")
    List<Customer> findbyPhoneNumber(@Param("number") String number);

    // Maybe multiple update methods that then can be called in one method to update all
    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.firstName = :firstName, c.lastName = :lastName WHERE c.customerId = :id")
    void updateFirstNameAndLastName(
            @Param("firstName") String firstName,
            @Param("lastName") String lastName,
            @Param("id") Integer id
    );

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.phoneNumber = :number WHERE c.customerId = :id")
    void updatePhoneNumber(
            @Param("phoneNumber") String phoneNumber,
            @Param("id") Integer id
    );

    @Transactional
    @Modifying
    @Query("UPDATE Customer c SET c.address = :address WHERE c.customerId = :id")
    void updateAddress(
            @Param("address") String address,
            @Param("id") Integer id
    );
}
