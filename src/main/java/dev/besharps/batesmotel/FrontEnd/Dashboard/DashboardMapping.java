package dev.besharps.batesmotel.FrontEnd.Dashboard;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/dashboard")
public class DashboardMapping {
    public String Dashboard(){
        return "Dashboard";
    }
}
