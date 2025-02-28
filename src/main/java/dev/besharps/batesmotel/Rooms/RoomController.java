package dev.besharps.batesmotel.Rooms;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomController {

    private final RoomRepository roomRepository;
    public RoomController(RoomRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //Incomplete but an example of what it is that we can do to retrieve our information from our database and
    //  expose the data to the front end.
    List<Room> findAll() {
        return roomRepository.findAll();
    }

}
