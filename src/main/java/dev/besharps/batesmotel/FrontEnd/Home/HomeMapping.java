package dev.besharps.batesmotel.FrontEnd.Home;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HomeMapping {

    @GetMapping("/")
    public String home() {
        return "home";
    }


}
