package dev.besharps.batesmotel.RepositoryTests;

import dev.besharps.batesmotel.BatesMotelApplication;
import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import dev.besharps.batesmotel.DB.Customer.CustomerService;
import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
import dev.besharps.batesmotel.DB.Payment.Payments;
import dev.besharps.batesmotel.DB.User.UserRepository;
import dev.besharps.batesmotel.DB.UserType.UserType;
import dev.besharps.batesmotel.DB.UserType.UserTypeRepository;
import dev.besharps.batesmotel.Exceptions.CustomerNotFoundException;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.logging.Logger;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = BatesMotelApplication.class)
class CustomerRepositoryTest {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private UserTypeRepository userTypeRepository;

    @Autowired
    private PaymentRepository paymentRepository;

    @Autowired
    private CustomerService customerService;

    @Test
    @Transactional
    @Rollback(false)
    public void createCustomerTest() {
        UserType userType = UserType.builder()
                .typeName("Customer")
                .build();
        userTypeRepository.save(userType);
        Customer customer = Customer.builder()
                .firstName("Lexie")
                .lastName("Brown")
                .address("1600 Pennsylvania Avenue NW")
                .carInformation("Hatchback")
                .phoneNumber("6120392232")
                .userType(userType)
                .build();
        customerRepository.save(customer);
    }

    @Test
    @Transactional
    @Rollback(false)
    public void createPaymentWithCustomer() {
        Customer customer = customerRepository.findById(1).orElseThrow(CustomerNotFoundException::new);
        Payments payment = paymentRepository.findById(1).orElseThrow(CustomerNotFoundException::new);
        //customer.addPayment(payment);
    }
}