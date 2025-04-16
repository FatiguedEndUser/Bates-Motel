package dev.besharps.batesmotel.FrontEnd.Dashboard;

import dev.besharps.batesmotel.DB.Bookings.Bookings;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
@RequestMapping("/dashboard")
public class DashboardMapping {

    @GetMapping
    public String Dashboard(Model model,
                            @RequestParam Bookings bookings,
                            @RequestParam(name = "id") String bookingId,
                            @RequestParam LocalDate startDate,
                            @RequestParam LocalDate endDate,
                            @RequestParam Bookings room){

        model.addAttribute("bookings", bookings);
        model.addAttribute("room", room);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("bookingId", bookingId);

        return "Dashboard";
    }

    public void populateDashboard(Model model,
                                  @RequestParam String firstName,
                                  @RequestParam String lastName,
                                  @RequestParam String email,
                                  @RequestParam int loyaltyPoints,
                                  @RequestParam int id){
        model.addAttribute("firstName", firstName);
        model.addAttribute("lastName", lastName);
        model.addAttribute("email", email);
        model.addAttribute("loyaltyPoints", loyaltyPoints);
        model.addAttribute("id", id);


    }
}
