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
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

@Controller
public class BookingMapping {

    private final BookingsRepository bookingsRepository;


    public BookingMapping(BookingsRepository bookingsRepository) {
        this.bookingsRepository = bookingsRepository;

    }

    // Mapping for the booking form page
    @GetMapping("/booking-form")
    public String formBooking(@RequestParam(required = false) String type, @RequestParam(required = false) String title, @RequestParam(required = false) long roomId,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
                              @RequestParam(required = false) Integer guests, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email, Model model) {
        model.addAttribute("roomType", type);
        model.addAttribute("roomTitle", title);
        model.addAttribute("roomId", roomId);
        model.addAttribute("checkin",   checkin);
        model.addAttribute("checkout",  checkout);
        model.addAttribute("guests",    guests);
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName",  lastName);
        model.addAttribute("email",     email);


        return "BookingForm";
    }

    // Mapping for the booking review page
    //I like it, was working on adding this to the database but lost in a merge. Will work on getting
    // added

    @PostMapping("/booking-form/review")
    public String reviewBooking(
            // carry customer info from payment form
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,

            // carry room lookup by ID
            @RequestParam(required = false) Integer roomId,

            // carry all booking details
            @RequestParam String roomType,
            @RequestParam String roomTitle,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            @RequestParam int guests,
            @RequestParam String roomPreference,
            @RequestParam String floorPreference,

            Model model
    ) {
        // customer info
        model.addAttribute("firstName",      firstName);
        model.addAttribute("lastName",       lastName);
        model.addAttribute("email",          email);

        // room info
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomType",       roomType);
        model.addAttribute("roomTitle",      roomTitle);

        // booking info
        model.addAttribute("checkin",        checkin);
        model.addAttribute("checkout",       checkout);
        model.addAttribute("guests",         guests);
        model.addAttribute("roomPreference", roomPreference);
        model.addAttribute("floorPreference",floorPreference);

        return "BookingReview";
    }

    @GetMapping("/booking/confirm")
    public String showConfirmation(
            @ModelAttribute("bookingId") Integer bookingId,
            Model model
    ) {
        // 1) Fetch the persisted booking by ID
        Bookings booking = bookingsRepository.findById(bookingId)
                .orElseThrow(() -> new IllegalArgumentException("Booking not found: " + bookingId));

        // 2) Push each field into the model so your Thymeleaf template can pick them up
        model.addAttribute("roomType",       booking.getRoomType());
        model.addAttribute("roomTitle",      booking.getRoomTitle());
        model.addAttribute("checkin",        booking.getStartDate());
        model.addAttribute("checkout",       booking.getEndDate());
        model.addAttribute("guests",         booking.getGuests());
        model.addAttribute("roomPreference", booking.getRoomPreference());
        model.addAttribute("floorPreference",booking.getFloorPreference());

        return "BookingConfirmation";
    }

    // Mapping for the "View My Bookings" page
    @GetMapping("/bookings")
    public String viewBookings(Model model) {
        List<Bookings> bookings = bookingsRepository.findAll();
        model.addAttribute("bookings", bookings);
        return "ViewBookings";
    }

    // CANCEL A BOOKING
    @PostMapping("/bookings/delete/{id}")
    public String deleteBooking(@PathVariable("id") int id, RedirectAttributes ra) {
        bookingsRepository.deleteById(id);
        ra.addFlashAttribute("cancelMessage", "Your booking has been canceled.");
        return "redirect:/bookings";
    }
}
