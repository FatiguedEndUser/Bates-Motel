package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import dev.besharps.batesmotel.Exceptions.BookingNotFoundException;
import dev.besharps.batesmotel.Exceptions.CustomerNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

//Mapping needs to be changed and access limited too admins or clerks
@RestController
@RequestMapping("/bookings")
public class BookingsController{
    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private BookingService bookingService;

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find-all")
    List<Bookings> findAll(){
        return bookingsRepository.findAll();
    }

    //This will find the id and assign it to bookings. if it is empty it will throw a http error
    //If not it will return our id
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Bookings> findById(@PathVariable Integer id) {
        Optional<Bookings> bookings = bookingsRepository.findById(id);
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException();
        }
        return bookings;
    }

    //POST
    //Post methods might need parameters that fill in from a form
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Bookings bookings) {
        bookingsRepository.save(bookings);
    }

    //PUT
    //WILL NEED UPDATE METHOD IMPLEMENTED IN REPO
    //What fields should be updatable?
    // - services
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/")
    Bookings updateStartDate(@Valid @RequestBody Bookings bookings, @PathVariable Date startDate) {
        //TODO implement update method
        return bookingService.updateStartDate(bookings, startDate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/date/{id}")
    void updatePhoneNumber(@Valid @PathVariable Integer id, @RequestParam LocalDate startDate
    , @RequestParam LocalDate endDate) {
        Bookings booking = bookingsRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new BookingNotFoundException();
        }
        if (startDate.isAfter(endDate)) {
            throw new BookingNotFoundException();
        }
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        bookingsRepository.save(booking);
    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        bookingsRepository.deleteById(id);
    }
}
