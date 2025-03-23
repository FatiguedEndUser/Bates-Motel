package dev.besharps.batesmotel.DB.Customer;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public void updateCustomer(int customerId, String firstName, String lastName) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        customer.setFirstName(firstName);
        customer.setLastName(lastName);

        customerRepository.save(customer);


    }
}
