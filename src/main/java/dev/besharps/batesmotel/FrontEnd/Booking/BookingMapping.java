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
    public BookingMapping(BookingsRepository bookingsRepository,
                          CustomerRepository customerRepository,
                          RoomsRepository roomsRepository) {
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
    public String reviewBooking(@RequestParam("roomTitle") String roomTitle,
                                @RequestParam("checkin") String checkin,
                                @RequestParam("checkout") String checkout,
                                @RequestParam("guests") int guests,
                                @RequestParam("roomPreference") String roomPreference,
                                @RequestParam("floorPreference") String floorPreference,
                                Model model) {
        model.addAttribute("roomTitle", roomTitle);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("roomPreference", roomPreference);
        model.addAttribute("floorPreference", floorPreference);

        model.addAttribute("Booking", new Bookings());

        return "BookingReview";
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

    @PostMapping("/createBooking")
    public String createBooking(@ModelAttribute Model model,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String roomType,
                                @RequestParam LocalDate checkin,
                                @RequestParam LocalDate checkout,
                                @RequestParam Rooms room){
        //LOGIC FOR CHOOSING ROOM
        bookingsRepository.save(new Bookings(new Customer(firstName, lastName, email), roomType, checkin, checkout, room));

        return "redirect:/payment";
    }
}
