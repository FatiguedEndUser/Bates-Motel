package dev.besharps.batesmotel.FrontEnd.Booking;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingFormMapping {

    @GetMapping("/form")
    public String FormBooking(@RequestParam(required = false) String type,
                              @RequestParam(required = false) String title,
                              Model model) {
        model.addAttribute("roomType", type);
        model.addAttribute("roomTitle", title);
        return "BookingForm";
    }
}
