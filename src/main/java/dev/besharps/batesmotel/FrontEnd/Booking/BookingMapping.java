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
                              @RequestParam(required = false) Long roomId,

                              Model model) {
        model.addAttribute("roomType", type);
        model.addAttribute("roomTitle", title);
        model.addAttribute("roomId", roomId);


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
            @RequestParam Integer roomId,

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
        model.addAttribute("roomId",         roomId);
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
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,

            @RequestParam Integer roomId,
            @RequestParam String roomType,
            @RequestParam String roomTitle,

            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
            LocalDate checkout,

            @RequestParam int    guests,
            @RequestParam String roomPreference,
            @RequestParam String floorPreference,

            RedirectAttributes ra
    ) {
        // persist the customer
        Customer customer = new Customer(firstName, lastName, email);
        customerRepository.save(customer);

        // load the room
        Rooms room = roomsRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        // build & save booking
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

        return "BookingConfirmation";
    }

    @PostMapping("/createBooking")
    public String createBooking(@ModelAttribute Model model,
                                @RequestParam String firstName,
                                @RequestParam String lastName,
                                @RequestParam String email,
                                @RequestParam String roomType,
                                @RequestParam LocalDate checkin,
                                @RequestParam LocalDate checkout,
                                @RequestParam Rooms room) {
        //LOGIC FOR CHOOSING ROOM
        bookingsRepository.save(new Bookings(new Customer(firstName, lastName, email), roomType, checkin, checkout, room));

        return "redirect:/payment";
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
    public String deleteBooking(@PathVariable("id") int id) {
        bookingsRepository.deleteById(id);
        return "redirect:/bookings";
    }
}
