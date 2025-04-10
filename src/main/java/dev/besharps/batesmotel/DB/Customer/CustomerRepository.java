package dev.besharps.batesmotel.DB.Customer;

import dev.besharps.batesmotel.DB.Payment.Payments;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface CustomerRepository extends JpaRepository<Customer, Integer> {
    @Query("SELECT c FROM Customer c WHERE LOWER(c.firstName) = LOWER(:firstName)")
    List<Customer> findByFirstName(@Param("firstName") String firstName);

    @Query("SELECT c FROM Customer c WHERE LOWER(c.lastName) = LOWER(:lastName)")
    List<Customer> findByLastName(@Param("lastName") String lastName);
    List<Customer> findByPhoneNumberContaining(String number);

    @Query("SELECT p FROM Customer c JOIN c.payments p WHERE c.customerId = :id")
    List<Payments> findPaymentsByCustomerId(@Param("id") Integer customerId);
}
