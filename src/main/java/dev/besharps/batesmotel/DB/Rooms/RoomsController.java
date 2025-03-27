package dev.besharps.batesmotel.DB.Rooms;

//DEPENDENCY IMPORTS
import jakarta.validation.Valid;
import dev.besharps.batesmotel.Exceptions.RoomNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//STANDARD LIB
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/rooms")
public class RoomsController {
    @Autowired
    private RoomsRepository roomsRepository;

    @Autowired
    private RoomsService roomsService;

    //GET
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find-all")
    List<Rooms> findAll() {
        return roomsRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Rooms> findById(@PathVariable Integer id) {
        Optional<Rooms> rooms = roomsRepository.findById(id);
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException();
        }
        return rooms;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/type/{roomType}")
    List<Rooms> findByRoomType(@PathVariable String roomType) {
        List<Rooms> rooms = roomsRepository.findByRoomType(roomType);
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException();
        }
        return rooms;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/number/{roomNumber}")
    List<Rooms> findByRoomNumber(@PathVariable Long roomNumber) {
        List<Rooms> rooms = roomsRepository.findByRoomNumber(roomNumber);
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException();
        }
        return rooms;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/available")
    List<Rooms> findAvailableRooms() {
        List<Rooms> rooms = roomsRepository.findByAvailableTrue();
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException();
        }
        return rooms;
    }

    //POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("")
    void create(@Valid @RequestBody Rooms room) {
        roomsRepository.save(room);
    }

    //PUT
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/room")
    public Rooms update(@Valid @RequestBody Rooms room) {
        if (!roomsRepository.existsById(room.getRoomId().intValue())) {
            throw new RoomNotFoundException();
        }
        return roomsRepository.save(room);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/type/{id}")
    void updateRoomType(@PathVariable Integer id, @RequestParam String roomType) {
        Rooms room = roomsRepository.findById(id).orElse(null);
        if (room == null) {
            throw new RoomNotFoundException();
        }
        room.setRoomType(roomType);
        roomsRepository.save(room);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/availability/{id}")
    void updateAvailability(@PathVariable Integer id, @RequestParam boolean available) {
        Rooms room = roomsRepository.findById(id).orElse(null);
        if (room == null) {
            throw new RoomNotFoundException();
        }
        room.setAvailable(available);
        roomsRepository.save(room);
    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        roomsRepository.deleteById(id);
    }
}