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
                              @RequestParam(required = false) long roomId,
                              @RequestParam(required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
                              @RequestParam(required = false)
                                  @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
                              @RequestParam(required = false) Integer guests,
                              @RequestParam(required = false) String firstName,
                              @RequestParam(required = false) String lastName,
                              @RequestParam(required = false) String email,

                              Model model) {
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

    @GetMapping("/booking/done")
    public String showBookingReview(
            @RequestParam Integer roomId,
            @RequestParam String roomType,
            @RequestParam String roomTitle,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam int    guests,
            @RequestParam String roomPreference,
            @RequestParam String floorPreference,
            Model model
        ) {

        System.out.println("\n==== DEBUG booking/done ====");
        System.out.println("Room ID: " + roomId);
        System.out.println("Type: " + roomType);
        System.out.println("===============\n");

        model.addAttribute("roomType", roomType);
        model.addAttribute("roomId", roomId);
        model.addAttribute("roomTitle", roomTitle);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("roomPreference", roomPreference);
        model.addAttribute("floorPreference", floorPreference);

        return "BookingReview";
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


            RedirectAttributes ra
            //Model model
    ) {

        System.out.println("\n==== DEBUG /booking-form/review ====");
        System.out.println("Room ID: " + roomId);
        System.out.println("Type: " + roomType);
        System.out.println("===============\n");

        // customer info
        ra.addAttribute("firstName",      firstName);
        ra.addAttribute("lastName",       lastName);
        ra.addAttribute("email",          email);

        // room info
        ra.addAttribute("roomId", roomId);
        ra.addAttribute("roomType",       roomType);
        ra.addAttribute("roomTitle",      roomTitle);

        // booking info
        ra.addAttribute("checkin",        checkin);
        ra.addAttribute("checkout",       checkout);
        ra.addAttribute("guests",         guests);
        ra.addAttribute("roomPreference", roomPreference);
        ra.addAttribute("floorPreference",floorPreference);

        return "redirect:/payment";
    }

    // saves and redirects to the GET below
    @PostMapping("/booking/confirm")
    public String confirmBooking(
            Customer customer,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,

            @RequestParam(required = false) Integer roomId,
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
        System.out.println("DEBUG: First name: " + firstName + " Last name: " + lastName + " Email: " + email);
        // persist the customer
        customer = new Customer(firstName, lastName, email);
        customerRepository.save(customer);

        Customer savedCustomer = customerRepository.findById(customer.getCustomerId()).orElse(null);
        System.out.println("DEBUG: Customer after save: " + savedCustomer);


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

        // Add payment info in here when confirm booking is clicked
        ra.addAttribute("roomType",        roomType);
        ra.addAttribute("roomTitle",       roomTitle);
        ra.addAttribute("checkin",         checkin);
        ra.addAttribute("checkout",        checkout);
        ra.addAttribute("guests",          guests);
        ra.addAttribute("roomPreference",  roomPreference);
        ra.addAttribute("floorPreference", floorPreference);

        // redirect to the GET below
       //return "redirect:/booking-form";
        //return "redirect:/booking/confirm";
        return "redirect:/payment/";
    }

    //  renders the confirmation page
    @GetMapping("/booking/confirm")
    public String showConfirmation(Model model) {
        System.out.println("DEBUG: Confirmation");
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
    public String deleteBooking(
            @PathVariable("id") int id,
            RedirectAttributes ra
    ) {
        bookingsRepository.deleteById(id);
        ra.addFlashAttribute("cancelMessage", "Your booking has been canceled.");
        return "redirect:/bookings";
    }
}
