package dev.besharps.batesmotel.DB.Bookings;

import dev.besharps.batesmotel.Exceptions.BookingNotFoundException;
import jakarta.validation.Valid;
import lombok.NoArgsConstructor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

//Mapping needs to be changed and access limited too admins or clerks
@RestController
@RequestMapping("/bookings")
@NoArgsConstructor
public class BookingsController{
    private BookingsRepository bookingsRepository;

    public BookingsController(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    //GET
    @Query("SELECT Book FROM Bookings book")
    @GetMapping("/find-all")
    List<Bookings> findAllBookings(){
        return bookingsRepository.findAll();
    }

    //This will find the id and assign it to bookings. if it is empty it will throw a http error
    //If not it will return our id
    @Query("SELECT bookingId FROM Bookings WHERE bookingId = :id")
    @GetMapping("/{id}")
    Optional<Bookings> findBookingById(@PathVariable Integer id) {
        Optional<Bookings> bookings = bookingsRepository.findById(id);
        if (bookings.isEmpty()) {
            throw new BookingNotFoundException();
        }
        return bookings;
    }

    //POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void createBookings(@Valid @RequestBody Bookings bookings) {
        bookingsRepository.save(bookings);
    }

    //PUT
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void updateBookings(@Valid @RequestBody Bookings bookings, @PathVariable String id) {
        bookingsRepository.save(bookings);
    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteBookings(@PathVariable Integer id) {
        bookingsRepository.deleteById(id);
    }

}
