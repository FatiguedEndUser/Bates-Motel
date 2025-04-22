package dev.besharps.batesmotel.DB.Customer;

import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {
    private final CustomerRepository customerRepository;

    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    @Transactional
    Customer updateNew(Customer customer, CustomerDetails myDetails) {
        if (myDetails.car_information() != null) {
            customer.setCarInformation(myDetails.car_information());
        }
        if (myDetails.firstName() != null) {
            customer.setFirstName(myDetails.firstName());
        }
        if (myDetails.lastName() != null) {
            customer.setLastName(myDetails.lastName());
        }
        if (myDetails.phoneNumber() != null) {
            customer.setPhoneNumber(myDetails.phoneNumber().toString());
        }
        return customerRepository.save(customer);
    }
}
