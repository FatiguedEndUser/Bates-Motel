package dev.besharps.batesmotel.FrontEnd.Payment;

import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
import dev.besharps.batesmotel.DB.Payment.Payments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import dev.besharps.batesmotel.DB.Bookings.Bookings;
import dev.besharps.batesmotel.DB.Bookings.BookingsRepository;
import dev.besharps.batesmotel.DB.Customer.Customer;
import dev.besharps.batesmotel.DB.Customer.CustomerRepository;
import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Rooms.RoomsRepository;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.time.LocalDate;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentMapping {
    private final PaymentRepository paymentRepository;
    private final CustomerRepository customerRepository;
    private final BookingsRepository bookingsRepository;
    private final RoomsRepository roomsRepository;

    @GetMapping
    public String payment(
            @RequestParam(required = false) Integer roomId,
            @RequestParam String roomType,
            @RequestParam String roomTitle,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam(defaultValue = "1") int guests,
            @RequestParam String roomPreference,
            @RequestParam String floorPreference,
            Model model
    ) {
          model.addAttribute("newPayment", new Payments());
          model.addAttribute("roomId", roomId);
          model.addAttribute("roomType", roomType);
          model.addAttribute("roomTitle", roomTitle);
          model.addAttribute("checkin", checkin);
          model.addAttribute("checkout", checkout);
          model.addAttribute("guests", guests);
          model.addAttribute("roomPreference", roomPreference);
          model.addAttribute("floorPreference", floorPreference);
          return "payment";
    }

    @PostMapping("/processPayment")
    public String processPayment(
            @ModelAttribute Payments payment,
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,
            @RequestParam Integer roomId,
            @RequestParam String roomType,
            @RequestParam String roomTitle,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkin,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate checkout,
            @RequestParam(defaultValue = "1") int guests,
            @RequestParam String roomPreference,
            @RequestParam String floorPreference,
            RedirectAttributes ra
    ) {
        // 1) save payment
        paymentRepository.save(payment);

        // 2) save customer
        Customer customer = new Customer(firstName, lastName, email);
        customerRepository.save(customer);

        // 3) load room and save booking
        Rooms room = roomsRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found: " + roomId));

        Bookings booking = Bookings.builder()
                .customer(customer)
                .room(room)
                .roomType(roomType)
                .roomTitle(roomTitle)
                .startDate(checkin)
                .endDate(checkout)
                .guests(guests)
                .roomPreference(roomPreference)
                .floorPreference(floorPreference)
                .build();
        bookingsRepository.save(booking);

        // 4) pass bookingId or other flash attrs
        ra.addFlashAttribute("bookingId", booking.getBookingId());
        return "redirect:/booking/confirm";
    }
}