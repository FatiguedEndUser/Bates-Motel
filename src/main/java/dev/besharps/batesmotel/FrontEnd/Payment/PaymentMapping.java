package dev.besharps.batesmotel.FrontEnd.Payment;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentMapping {

    @GetMapping("/payment")
    public String payment() {
        return "payment";
    }

}
