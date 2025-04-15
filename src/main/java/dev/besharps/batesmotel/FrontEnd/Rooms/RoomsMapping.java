package dev.besharps.batesmotel.FrontEnd.Rooms;

import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Rooms.RoomsController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@Controller
public class RoomsMapping {
    private RoomsController rooms;

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

    public Boolean checkAvaliability(Rooms room){
        List<Rooms> allRooms = rooms.findAll();
        if(room.isAvailable()){
            return true;
        }
        return false;
    }

}

