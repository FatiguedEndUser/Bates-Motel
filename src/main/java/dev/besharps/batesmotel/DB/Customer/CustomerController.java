package dev.besharps.batesmotel.DB.Customer;

import dev.besharps.batesmotel.Exceptions.BookingNotFoundException;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@NoArgsConstructor
public class CustomerController {
    private CustomerRepository customerRepository;
    public CustomerController(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    //GET
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find-all")
    List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Customer> findBookingById(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new BookingNotFoundException();
        }
        return customer;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{firstName}")
    Optional<Customer> findByFirstName(@PathVariable String firstName) {
        return Optional.of(customerRepository.findByLastName(firstName));
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{lastName}")
    Optional<Customer> findByLastName(@PathVariable String lastName) {
        return Optional.of(customerRepository.findByLastName(lastName));
    }

    //TODO: Implement findByPhoneNumber, findByEmail

    //POST
    //Post methods might need parameters that fill in from a form
    @ResponseStatus(HttpStatus.CREATED)
    @GetMapping("")
    void createCustomer(@Valid @RequestBody Customer customer) {
        customerRepository.save(customer);
    }

    //PUT
    void updateCustomer(@Valid @RequestBody Customer customer) {

    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/{id}")
    void deleteBookingById(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }
}
