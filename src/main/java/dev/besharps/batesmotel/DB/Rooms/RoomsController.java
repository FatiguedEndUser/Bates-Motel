package dev.besharps.batesmotel.DB.Rooms;

//DEPENDENCY IMPORTS
import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//STANDARD LIB
import java.util.List;
import java.util.Optional;
import dev.besharps.batesmotel.Exceptions.RoomNotFoundException;

@RestController
@RequestMapping("/rooms")
//RequiredArcConstructor is nice but lets worry about refactoring code after we know everything is properly setup
//@RequiredArgsConstructor
public class RoomsController {
    @Autowired
    private RoomsRepository roomsRepository;

    //GET
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/rooms/find-all")
    List<Rooms> findAll() {
        return roomsRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/rooms/{id}")
    Optional<Rooms> findById(@PathVariable Integer id) {
        Optional<Rooms> rooms = roomsRepository.findById(id);
        if (rooms.isEmpty()) {
            throw new RoomNotFoundException();
        }
        return rooms;
    }

    //POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/rooms/")
    void create(@Valid @RequestBody Rooms room) {
        roomsRepository.save(room);
    }
    //PUT
    //What fields should be updatable?
    // -
    // -
    void update(@Valid @RequestBody Rooms room, @PathVariable Integer id) {
        //TODO: Implement update method
    }
    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/rooms/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        roomsRepository.deleteById(id);
    }

}
