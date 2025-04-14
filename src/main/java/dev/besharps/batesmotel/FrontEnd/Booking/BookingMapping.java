package dev.besharps.batesmotel.FrontEnd.Booking;
import dev.besharps.batesmotel.DB.Bookings.Bookings;
import dev.besharps.batesmotel.DB.Bookings.BookingsController;
import dev.besharps.batesmotel.DB.Bookings.BookingsRepository;
import dev.besharps.batesmotel.DB.Customer.Customer;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class BookingMapping {

    private BookingsRepository bookings;

    public BookingMapping(BookingsRepository bookings) {
        this.bookings = bookings;
    }

    @GetMapping("/form")
    public String FormBooking(Model model) {
//        model.addAttribute("roomType", type);
//        model.addAttribute("roomTitle", title);
        model.addAttribute("newBookings", new Bookings());
        return "BookingForm";
    }

    //NOT SURE WHAT RETURN TYPE WILL BE YET
    public String addBooking(@ModelAttribute Bookings booking,
                            @RequestParam LocalDate startDate ,
                            @RequestParam LocalDate endDate,
                            @RequestParam Customer customer
                            ){
        bookings.save(new Bookings(customer, startDate, endDate));
        return "redirect:/payment";
    }
}
