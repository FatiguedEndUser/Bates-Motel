package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.DB.Services.Services;
import dev.besharps.batesmotel.Exceptions.BookingNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

//Mapping needs to be changed and access limited too admins or clerks
@RestController
@RequestMapping("/bookings")
public class BookingsController{
    @Autowired
    private BookingsRepository bookingsRepository;

    @Autowired
    private BookingService bookingService;

    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/bookings/find-all")
    List<Bookings> findAll(){
        return bookingsRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/bookings/{id}")
    Optional<Bookings> findById(@PathVariable Integer id) {
        Optional<Bookings> bookings = bookingsRepository.findById(id);
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException();
        }
        return bookings;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/find/services/{id}")
    List<Services> findBookingsServices(@PathVariable Integer id){
        return bookingsRepository.findServicesByBookingId(id);
    };

    // TODO Wait for front-end to implement booking creation form
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create/booking/")
    void create(@Valid @RequestBody Bookings bookings) {
        bookingsRepository.save(bookings);
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PutMapping("/create/service/{id}")
    Bookings updateService(@PathVariable Integer id, @RequestParam int serviceType) {
        Bookings booking = bookingsRepository.findById(id).orElse(null);
        if (booking == null) {
            throw new BookingNotFoundException();
        }
        return bookingService.addService(booking, serviceType);
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
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Start date cannot be after end date");
        }
        booking.setStartDate(startDate);
        booking.setEndDate(endDate);
        bookingsRepository.save(booking);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/bookings/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        bookingsRepository.deleteById(id);
    }
}
