package dev.besharps.batesmotel.FrontEnd.Rooms;

import org.springframework.web.bind.annotation.RequestMapping;

@RequestMapping("/Rooms-Standard")
public class RoomsAdapter {
    public String RoomAdapter() {
        return "StandardRoom";
    }
}
