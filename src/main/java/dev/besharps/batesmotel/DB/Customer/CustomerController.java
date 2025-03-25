package dev.besharps.batesmotel.DB.Customer;

//DEPENDENCY IMPORTS
import dev.besharps.batesmotel.Exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//STANDARD LIB
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
public class CustomerController {

    @Autowired
    private CustomerRepository customerRepository;

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find-all")
    List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Customer> findById(@PathVariable Integer id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customer;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/first-name/{firstName}")
    List<Customer> findByFirstName(@PathVariable String firstName) {
        if (customerRepository.findByFirstName(firstName).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customerRepository.findByFirstName(firstName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/last-name/{lastName}")
        List<Customer> findByLastName(@PathVariable String lastName) {
        if (customerRepository.findByLastName(lastName).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customerRepository.findByLastName(lastName);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/phone-number/{number}")
    List<Customer> findByPhoneNumberContaining(@PathVariable String number) {
        if (customerRepository.findByPhoneNumberContaining(number).isEmpty()) {
            throw new CustomerNotFoundException();
        }
        return customerRepository.findByPhoneNumberContaining(number);
    }

    // TODO Wait for front end to create a customer form to implement creation of a customer
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Customer customer) {
        customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/customer")
    public Customer update(@Valid @RequestBody Customer customer) {
        if (!customerRepository.existsById(customer.getCustomerId())) {
            throw new CustomerNotFoundException();
        }
        return customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/first/{id}")
    void updateFirstName(@PathVariable Integer id, @RequestParam String firstName) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        customer.setFirstName(firstName);
        customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/last/{id}")
    void updateLastName(@PathVariable Integer id, @RequestParam String lastName) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        customer.setLastName(lastName);
        customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/phone/{id}")
    void updatePhoneNumber(@PathVariable Integer id, @RequestParam String number) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        customer.setPhoneNumber(number);
        customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/address/{id}")
    void updateAddress(@PathVariable Integer id, @RequestParam String address) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        customer.setAddress(address);
        customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/car/{id}")
    void updateCar(@PathVariable Integer id, @RequestParam String car) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) {
            throw new CustomerNotFoundException();
        }
        customer.setAddress(car);
        customerRepository.save(customer);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }
}
