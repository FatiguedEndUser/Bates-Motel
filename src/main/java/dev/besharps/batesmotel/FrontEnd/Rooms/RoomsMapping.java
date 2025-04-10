package dev.besharps.batesmotel.FrontEnd.Rooms;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class RoomsMapping {
    @GetMapping("/rooms/standard-rooms")
    public String RoomsStandard() {
        return "StandardRoom";
    }

    @GetMapping("/rooms/deluxe-rooms")
    public String RoomsDeluxe() {
        return "DeluxeRoom";
    }

    @GetMapping("/rooms/suite-rooms")
    public String RoomsSuite() {
        return "SuiteRoom";
    }


}

