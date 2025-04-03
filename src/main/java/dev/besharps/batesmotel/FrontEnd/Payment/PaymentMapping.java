package dev.besharps.batesmotel.FrontEnd.Payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/payment")
public class PaymentMapping {

    @GetMapping
    public String payment(Model model) {
        //model.addAttribute(newPayment, new Payment());
        return "payment";
    }

    //@PostMapping("")
    //public String ProcessPayment(@ModelAttribute Payment payment, Model model) {
        //DB
        //paymentRepository.save();
        //RETURN TO HOME
        //SHOULD RETURN TO USER PAGE OR SOMEWHERE ELSE
        //return "redirect:/";
    //}
}
