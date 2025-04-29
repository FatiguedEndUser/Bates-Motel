package dev.besharps.batesmotel.FrontEnd.Payment;

import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
import dev.besharps.batesmotel.DB.Payment.Payments;
import jakarta.servlet.http.HttpSession;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDate;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentMapping {
    private final PaymentRepository paymentRepository;

    @GetMapping
    public String payment(
            // Customer
            @RequestParam String firstName,
            @RequestParam String lastName,
            @RequestParam String email,

            @RequestParam(required = false) Integer roomId,
            @RequestParam String roomType,
            @RequestParam String roomTitle,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam int    guests,
            @RequestParam String roomPreference,
            @RequestParam String floorPreference,
            Model model,
            HttpSession session
    ) {
        System.out.println("DEBUG (GET): /payment/");

        // Set customer session
        session.setAttribute("firstName", firstName);
        session.setAttribute("lastName", lastName);
        session.setAttribute("email", email);

        // Set customer attribute
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
    public String ProcessPayment(
            @ModelAttribute Payments payment,
            @RequestParam Integer roomId,
            @RequestParam String roomType,
            @RequestParam String roomTitle,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam int    guests,
            @RequestParam String roomPreference,
            @RequestParam String floorPreference,

            RedirectAttributes ra
    ) {
        paymentRepository.save(payment);

        ra.addFlashAttribute("roomId", roomId);
        ra.addFlashAttribute("roomType", roomType);
        ra.addFlashAttribute("roomTitle", roomTitle);
        ra.addFlashAttribute("checkin", checkin);
        ra.addFlashAttribute("checkout", checkout);
        ra.addFlashAttribute("guests", guests);
        ra.addFlashAttribute("roomPreference", roomPreference);
        ra.addFlashAttribute("floorPreference", floorPreference);

        // Redirect to the booking review page
        return "redirect:/booking-form/review";
    }
}
