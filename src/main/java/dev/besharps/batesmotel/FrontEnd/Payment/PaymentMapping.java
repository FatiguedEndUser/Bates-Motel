package dev.besharps.batesmotel.FrontEnd.Payment;

import dev.besharps.batesmotel.DB.Payment.Payment;
import dev.besharps.batesmotel.DB.Payment.PaymentRepository;
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
        //model.addAttribute(newPayment, new Payment());
        return "payment";
    }

    @PostMapping("")
    public String ProcessPayment(@ModelAttribute Payment payment,
                                 Model model,
                                 @RequestParam(name = "name") String name,
                                 @RequestParam(name = "cardNumber") int cardNumber,
                                 @RequestParam(name = "expiryDate") int expireDate,
                                 @RequestParam(name = "cvv") int cvv,
                                 @RequestParam(name = "billingZip") int zip) {

        payment = paymentRepository.save(new Payment(name, cardNumber, expireDate, cvv, zip));

        //RETURN TO HOME
        //SHOULD RETURN TO USER PAGE OR SOMEWHERE ELSE
        return "redirect:/";
    }
}
