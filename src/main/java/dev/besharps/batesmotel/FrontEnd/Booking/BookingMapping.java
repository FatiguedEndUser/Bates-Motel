package dev.besharps.batesmotel.FrontEnd.Booking;

import dev.besharps.batesmotel.DB.Bookings.Bookings;
import dev.besharps.batesmotel.DB.Bookings.BookingsRepository;
import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Rooms.RoomsRepository;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
public class BookingMapping {

    private final BookingsRepository bookingsRepository;
    private final CustomerRepository customerRepository;
    private final RoomsRepository roomsRepository;
    public BookingMapping(BookingsRepository bookingsRepository, CustomerRepository customerRepository, RoomsRepository roomsRepository) {
        this.bookingsRepository = bookingsRepository;
        this.customerRepository = customerRepository;
        this.roomsRepository = roomsRepository;
    }

    // Mapping for the booking form page
    @GetMapping("/booking-form")
    public String formBooking(@RequestParam(required = false) String type,
                              @RequestParam(required = false) String title,
                              Model model) {
        model.addAttribute("roomType", type);
        model.addAttribute("roomTitle", title);
        return "BookingForm";
    }

    // Mapping for the booking review page
    //I like it i was working on adding this to the database but lost in in a merge. Will work on getting
    // added
    @PostMapping("/booking-form/review")
    public String reviewBooking(@RequestParam("roomType") String roomType,
                                @RequestParam("roomTitle") String roomTitle,
                                @RequestParam("checkin") String checkin,
                                @RequestParam("checkout") String checkout,
                                @RequestParam("guests") int guests,
                                @RequestParam("roomPreference") String roomPreference,
                                @RequestParam("floorPreference") String floorPreference,
                                Model model) {
        model.addAttribute("roomType", roomType);
        model.addAttribute("roomTitle", roomTitle);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("roomPreference", roomPreference);
        model.addAttribute("floorPreference", floorPreference);

        model.addAttribute("Booking", new Bookings());

        return "BookingReview";
    }

    //This will be the action for confirming review
    //which will then redirect to the payment page
    @PostMapping("/createBooking")
    public String createBooking(@ModelAttribute Model model,
                                @RequestParam Customer customer,
                                @RequestParam String roomType,
                                @RequestParam LocalDate checkin,
                                @RequestParam LocalDate checkout,
                                @RequestParam Rooms room){
        //Notice a discrepancy we have a customer in as a field for the DB but we dont have a customer name or other
        //required info. This will not post to the database, otherwise i can take the customer name from the payment info
        //but that is the next step so i would have to some how save that data from the payment page.

        bookingsRepository.save(new Bookings(customer, roomType, checkin, checkout, room));

        return "redirect:/payment";
    }

    // Mapping for the booking confirmation page
    @PostMapping("/booking/confirm")
    public String confirmBooking(@RequestParam("roomType") String roomType,
                                 @RequestParam("roomTitle") String roomTitle,
                                 @RequestParam("checkin") String checkin,
                                 @RequestParam("checkout") String checkout,
                                 @RequestParam("guests") int guests,
                                 @RequestParam("roomPreference") String roomPreference,
                                 @RequestParam("floorPreference") String floorPreference,
                                 Model model) {
        // Add all booking details to the model so that the confirmation page can display them
        model.addAttribute("roomType", roomType);
        model.addAttribute("roomTitle", roomTitle);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("roomPreference", roomPreference);
        model.addAttribute("floorPreference", floorPreference);
        return "BookingConfirmation";  // This loads BookingConfirmation.html
    }
}
