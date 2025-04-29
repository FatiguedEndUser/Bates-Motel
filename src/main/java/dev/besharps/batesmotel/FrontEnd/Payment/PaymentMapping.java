package dev.besharps.batesmotel.FrontEnd.Payment;

import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
import dev.besharps.batesmotel.DB.Payment.Payments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentMapping {
    private final PaymentRepository paymentRepository;

    @GetMapping
    public String payment(
            @RequestParam(required = false) Integer roomId,
            @RequestParam String roomType,
            @RequestParam String roomTitle,
            @RequestParam String checkin,
            @RequestParam String checkout,
            @RequestParam int    guests,
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
    public String ProcessPayment(@ModelAttribute Payments payment) {
                                /* @RequestParam(name = "name") String name,
                                 @RequestParam(name = "cardNumber") int cardNumber,
                                 @RequestParam(name = "expiryDate") LocalDate expireDate,
                                 @RequestParam(name = "cvv") int cvv,
                                 @RequestParam(name = "billingZip") int zip) {
        payment = Payments.builder()
                .name(name)
                .cardNumber(cardNumber)
                .date(expireDate)
                .cvv(cvv)
                .zip(zip)
                .build();*/
        paymentRepository.save(payment);
        return "redirect:/booking/confirm";
    }
}
