package dev.besharps.batesmotel.DB.Rooms;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/rooms")
public class RoomsController {

    private final RoomsRepository roomRepository;
    public RoomsController(RoomsRepository roomRepository) {
        this.roomRepository = roomRepository;
    }

    //Incomplete but an example of what it is that we can do to retrieve our information from our database and
    //  expose the data to the front end.
    List<Rooms> findAll() {
        return roomRepository.findAll();
    }

}
