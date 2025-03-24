package dev.besharps.batesmotel.DB.Customer;

import jakarta.transaction.Transactional;
import org.hibernate.service.spi.InjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.logging.Logger;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Transactional
    public void updateCustomer(int customerId, String firstName, String lastName, String address, String phone, String carinformation) {
        Customer customer = customerRepository.findById(customerId).orElse(null);
        if (customer == null) {
            throw new IllegalArgumentException("Customer not found");
        }

        // Maybe a better way to do this?
        if (firstName != null) {
            customer.setFirstName(firstName);
        }
        if (lastName != null) {
            customer.setLastName(lastName);
        }
        if (address != null) {
            customer.setAddress(address);
        }
        if (phone != null) {
            customer.setPhoneNumber(phone);
        }
        if (carinformation != null) {
            customer.setCarInformation(carinformation);
        }

        customerRepository.save(customer);
    }
}
