package dev.besharps.batesmotel.FrontEnd.Rooms;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoomsMapping {
    @GetMapping("/rooms/standard-rooms")
    public String RoomsStandard() {
        return "StandardRoom";
    }


}

