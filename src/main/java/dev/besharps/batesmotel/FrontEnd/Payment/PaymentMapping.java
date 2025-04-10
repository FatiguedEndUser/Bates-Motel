package dev.besharps.batesmotel.FrontEnd.Payment;

import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
import dev.besharps.batesmotel.DB.Payment.Payments;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/payment")
@RequiredArgsConstructor
public class PaymentMapping {
    private final PaymentRepository paymentRepository;

    @GetMapping
    public String payment(Model model) {
        model.addAttribute("Payment", new Payments());
        return "payment";
    }

    @PostMapping("/processPayment")
    public String ProcessPayment(@ModelAttribute Payments payment,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "cardNumber") int cardNumber,
                                 @RequestParam(name = "expiryDate") int expireDate,
                                 @RequestParam(name = "cvv") int cvv,
                                 @RequestParam(name = "billingZip") int zip) {

        paymentRepository.save(new Payments(name, cardNumber, expireDate, cvv, zip));

        //RETURN TO HOME
        //SHOULD RETURN TO USER PAGE OR SOMEWHERE ELSE
        return "redirect:/";
    }
}
