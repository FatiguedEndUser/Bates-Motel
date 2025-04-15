package dev.besharps.batesmotel.FrontEnd.Booking;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class BookingMapping {

    // Mapping for the booking form page
    @GetMapping("/form")
    public String formBooking(@RequestParam(required = false) String type,
                              @RequestParam(required = false) String title,
                              Model model) {
        model.addAttribute("roomType", type);
        model.addAttribute("roomTitle", title);
        return "BookingForm";
    }

    // Mapping for the booking review page
    @PostMapping("/booking/review")
    public String reviewBooking(@RequestParam("roomType") String roomType,
                                @RequestParam("roomTitle") String roomTitle,
                                @RequestParam("checkin") String checkin,
                                @RequestParam("checkout") String checkout,
                                @RequestParam("guests") int guests,
                                @RequestParam("roomPreference") String roomPreference,
                                @RequestParam("floorPreference") String floorPreference,
                                Model model) {
        model.addAttribute("roomType", roomType);
        model.addAttribute("roomTitle", roomTitle);
        model.addAttribute("checkin", checkin);
        model.addAttribute("checkout", checkout);
        model.addAttribute("guests", guests);
        model.addAttribute("roomPreference", roomPreference);
        model.addAttribute("floorPreference", floorPreference);
        return "BookingReview";
    }
}

