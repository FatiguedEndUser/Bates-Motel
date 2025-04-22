package dev.besharps.batesmotel.FrontEnd.Offers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/offers")
public class OffersMapping {

    @GetMapping
    public String Offers() {
        return "Offers";
    }


}
