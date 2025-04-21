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
    Customer updateNew(Customer customer, CustomerDetails myDetails) {
        if (myDetails.car_information() != null) {
            customer.setCarInformation(myDetails.car_information());
        }
        if (myDetails.address() != null) {
            customer.setAddress(myDetails.address());
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
