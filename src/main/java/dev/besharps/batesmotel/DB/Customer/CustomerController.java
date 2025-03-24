package dev.besharps.batesmotel.DB.Customer;

//DEPENDENCY IMPORTS
import dev.besharps.batesmotel.Exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//STANDARD LIB
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/customer")
@RequiredArgsConstructor
public class CustomerController {
    @Autowired
    private CustomerRepository customerRepository;

    //GET
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

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{firstName}")
//    Optional<Customer> findByFirstName(@PathVariable String firstName) {
//        return Optional.of(customerRepository.findByFirstName(firstName));
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/{lastName}")
//    Optional<Customer> findByLastName(@PathVariable String lastName) {
//        return Optional.of(customerRepository.findByLastName(lastName));
//    }

    //TODO: Implement findByPhoneNumber, findByEmail

    //POST
    //Post methods might need parameters that fill in from a form
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Customer customer) {
        customerRepository.save(customer);
    }

    //PUT
    //What fields should be updatable?
    // - lastName
    // - phoneNumber
    // - address
    // - carInformation
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("")
    void updateLastName(@Valid @RequestBody Customer customer) {

    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @GetMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        customerRepository.deleteById(id);
    }
}
