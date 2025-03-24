package dev.besharps.batesmotel.FrontEnd.Rooms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class RoomsMapping {
    @GetMapping("/Standard-Rooms")
    public String RoomsStandard() {
        return "StandardRoom";
    }
}

