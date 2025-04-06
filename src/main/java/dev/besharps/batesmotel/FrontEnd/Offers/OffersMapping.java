package dev.besharps.batesmotel.FrontEnd.Offers;

import dev.besharps.batesmotel.DB.Rooms.Rooms;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;

@Controller
@RequestMapping("/offers")
public class OffersMapping {

    @GetMapping
    public String Offers(Model model) {
        //model.addAttribute();
        return "Offers";
    }


}
