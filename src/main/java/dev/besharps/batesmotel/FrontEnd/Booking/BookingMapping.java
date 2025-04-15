package dev.besharps.batesmotel.FrontEnd.Booking;
import dev.besharps.batesmotel.DB.Bookings.Bookings;
import dev.besharps.batesmotel.DB.Bookings.BookingsController;
import dev.besharps.batesmotel.DB.Bookings.BookingsRepository;
import dev.besharps.batesmotel.DB.Customer.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/booking-form")
public class BookingMapping {

    private final BookingsRepository bookingsRepository;

    public BookingMapping(BookingsRepository bookings) {
        this.bookingsRepository = bookings;
    }

    @GetMapping
    public String FormBooking(Model model) {
        model.addAttribute("newBookings", new Bookings());
        return "BookingForm";
    }

    //NOT SURE WHAT RETURN TYPE WILL BE YET
    @PostMapping("/createBooking")
    public String createBooking(@ModelAttribute Bookings booking,
                            @RequestParam LocalDate startDate ,
                            @RequestParam LocalDate endDate,
                            @RequestParam Customer customer
                            ){
        //SAVE TO DB
        bookingsRepository.save(new Bookings(customer, startDate, endDate));
        //PUSHES USER TOO PAYMENT
        return "redirect:/payment";
    }
}
