package dev.besharps.batesmotel.FrontEnd.Booking;

import dev.besharps.batesmotel.DB.Bookings.Bookings;
import dev.besharps.batesmotel.DB.Bookings.BookingsRepository;
import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Rooms.RoomsRepository;
import jakarta.servlet.http.HttpSession;
import org.springframework.cglib.core.Local;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;
import java.util.List;

import static org.springframework.web.util.WebUtils.getSessionAttribute;

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
    public String formBooking(@RequestParam(required = false) String type, @RequestParam(required = false) String title, @RequestParam(required = false) long roomId,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
                              @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
                              @RequestParam(required = false) Integer guests, @RequestParam(required = false) String firstName, @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email, Model model,
                              HttpSession session
    ) {
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

    // Runs first
    @GetMapping("/booking-form/review")
    public String reviewBooking(
            // carry customer info from payment form
            //@RequestParam String firstName,
            //@RequestParam String lastName,
            //@RequestParam String email,

            // carry room lookup by modelAttribute
            @ModelAttribute("roomId") Integer roomId,

            // carry room lookup by ID
            //@RequestParam(required = false) Integer roomId,

            // carry all booking details via modelAttribute
            @ModelAttribute("roomType") String roomType,
            @ModelAttribute("roomTitle") String roomTitle,
            @ModelAttribute("checkin") LocalDate checkin,
            @ModelAttribute("checkout") LocalDate checkout,
            @ModelAttribute("guests") int guests,
            @ModelAttribute("roomPreference") String roomPreference,
            @ModelAttribute("floorPreference") String floorPreference,

            // carry all booking details
            //@RequestParam String roomType,
            //@RequestParam String roomTitle,
            //@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            //@RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            //@RequestParam int guests,
            //@RequestParam String roomPreference,
            //@RequestParam String floorPreference,
            HttpSession session,

            Model model
    ) {
        System.out.println("DEBUG (POST): /booking-form/review");

        // customer info
        //model.addAttribute("firstName",      firstName);
        //model.addAttribute("lastName",       lastName);
        //model.addAttribute("email",          email);

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

    // saves and redirects to the GET below
    @PostMapping("/booking/confirm")
    public String confirmBooking(
            @RequestParam(required = false) Integer roomId, @RequestParam String roomType, @RequestParam String roomTitle,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            @RequestParam int    guests, @RequestParam String roomPreference, @RequestParam String floorPreference,
            RedirectAttributes ra,
            HttpSession session
    ) {
        System.out.println("DEBUG (POST): /booking/confirm");

        // Retrieve session attributes
        String firstName = session.getAttribute("firstName").toString();
        String lastName = session.getAttribute("lastName").toString();
        String email = session.getAttribute("email").toString();

        // persist the customer
        Customer customer = new Customer(firstName, lastName, email);
        customerRepository.save(customer);

        // load the room
        Rooms room = roomsRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

         //build & save booking
        Bookings booking = Bookings.builder()
                .customer(customer)
                .room(room)
                .roomType(roomType)
                .roomTitle(roomTitle)
                .startDate(checkin)
                .endDate(checkout)
                .guests(guests)
                .build();
        bookingsRepository.save(booking);

        ra.addFlashAttribute("roomType",        roomType);
        ra.addFlashAttribute("roomTitle",       roomTitle);
        ra.addFlashAttribute("checkin",         checkin);
        ra.addFlashAttribute("checkout",        checkout);
        ra.addFlashAttribute("guests",          guests);
        ra.addFlashAttribute("roomPreference",  roomPreference);
        ra.addFlashAttribute("floorPreference", floorPreference);

        // redirect to the GET below
        return "redirect:/booking/confirm";
    }


    //  renders the confirmation page
    @GetMapping("/booking/confirm")
    public String showConfirmation(Model model) {
        System.out.println("DEBUG (GET): /booking/confirm");
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
