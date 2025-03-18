package dev.besharps.batesmotel.DB.Bookings;

//DEPENDENCY IMPORTS
import dev.besharps.batesmotel.Exceptions.BookingNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//STANDARD LIB
import java.util.List;
import java.util.Optional;

//Mapping needs to be changed and access limited too admins or clerks
@RestController
@RequestMapping("/bookings")
@RequiredArgsConstructor
public class BookingsController{
    private BookingsRepository bookingsRepository;
    public BookingsController(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;
    }

    //GET
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
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/{id}")
    void update(@Valid @RequestBody Bookings bookings, @PathVariable String id) {
        //TODO implement update method
        //bookingsRepository.update(bookings);
    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        bookingsRepository.deleteById(id);
    }
}
