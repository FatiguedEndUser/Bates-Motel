package dev.besharps.batesmotel.FrontEnd.Rooms;

import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Rooms.RoomsRepository;
import dev.besharps.batesmotel.DB.Rooms.RoomsService;
import dev.besharps.batesmotel.Exceptions.RoomNotFoundException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/rooms")
public class RoomsMapping{
    private final RoomsRepository roomsRepository;
    private final RoomsService roomsService;

    public RoomsMapping(RoomsRepository roomsRepository, RoomsService roomsService) {
        this.roomsRepository = roomsRepository;
        this.roomsService = roomsService;
    }

    // Frontend view mappings
    @GetMapping("/standard-rooms")
    public String standardRooms(Model model) {
         List<Rooms> standardRooms = roomsService.getRoomsByType("Standard");
         model.addAttribute("rooms", standardRooms);
        return "StandardRoom";
    }

    @GetMapping("/deluxe-rooms")
    public String deluxeRooms(Model model) {
        List<Rooms> deluxeRooms = roomsService.getRoomsByType("Deluxe");
        model.addAttribute("rooms", deluxeRooms);
        return "DeluxeRoom";
    }

    @GetMapping("/suite-rooms")
    public String suiteRooms(Model model) {
        List<Rooms> suiteRooms = roomsService.getRoomsByType("Suite");
        model.addAttribute("rooms", suiteRooms);
        return "SuiteRoom";
    }

    @GetMapping("/search")
    public String searchRooms(
            @RequestParam(required = false) String roomType,
            @RequestParam(required = false) Double maxPrice,
            @RequestParam(required = false) String bedType,
            Model model) {
        List<Rooms> rooms = roomsService.searchRooms(roomType, maxPrice, bedType);
        model.addAttribute("rooms", rooms);
        model.addAttribute("roomType", roomType);
        model.addAttribute("maxPrice", maxPrice);
        model.addAttribute("bedType", bedType);
        return "RoomSearch";
    }

    // Admin page for managing rooms
    @GetMapping("/admin/manage")
    public String manageRooms(Model model) {
        List<Rooms> allRooms = roomsService.getAllRooms();
        model.addAttribute("rooms", allRooms);
        model.addAttribute("newRoom", new Rooms());
        return "RoomManagement";
    }

    @PostMapping("/admin/add")
    public String addRoom(@ModelAttribute Rooms room) {
        roomsService.saveRoom(room);
        return "redirect:/rooms/admin/manage";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Rooms> room = roomsRepository.findById(id);
        if (room.isEmpty()) {
            throw new RoomNotFoundException();
        }
        model.addAttribute("room", room.get());
        return "EditRoom";
    }

    @PostMapping("/admin/update/{id}")
    public String updateRoom(@PathVariable Integer id, @ModelAttribute Rooms room) {
        roomsService.updateRoom(
                room.getRoomId(),
                room.getRoomName(),
                room.getRoomType(),
                room.getPricePerNight(),
                room.getBedConfiguration(),
                room.getAppliances(),
                room.isAvailable(),
                room.getImageUrl(),
                room.getDescription()
        );
        return "redirect:/rooms/admin/manage";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteRoom(@PathVariable Integer id) {
        roomsService.deleteRoom(id);
        return "redirect:/rooms/admin/manage";
    }

// REST API endpoints
//    @ResponseStatus(HttpStatus.FOUND)
//    @GetMapping("/api/all")
//    @ResponseBody
//    List<Rooms> findAll() {
//        return roomsRepository.findAll();
//    }

//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/api/{id}")
//    @ResponseBody
//    Optional<Rooms> findById(@PathVariable Integer id) {
//        Optional<Rooms> rooms = roomsRepository.findById(id);
//        if (rooms.isEmpty()) {
//            throw new RoomNotFoundException();
//        }
//        return rooms;
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @GetMapping("/api/type/{roomType}")
//    @ResponseBody
//    List<Rooms> findByRoomType(@PathVariable String roomType) {
//        List<Rooms> rooms = roomsRepository.findByRoomType(roomType);
//        if (rooms.isEmpty()) {
//            throw new RoomNotFoundException();
//        }
//        return rooms;
//    }

//    @ResponseStatus(HttpStatus.CREATED)
//    @PostMapping("/api/create")
//    @ResponseBody
//    void create(@Valid @RequestBody Rooms room) {
//        roomsRepository.save(room);
//    }
//
//    @ResponseStatus(HttpStatus.OK)
//    @PutMapping("/api/update")
//    @ResponseBody
//    public Rooms update(@Valid @RequestBody Rooms room) {
//        if (!roomsRepository.existsById(room.getRoomId().intValue())) {
//            throw new RoomNotFoundException();
//        }
//        return roomsRepository.save(room);
//    }
//
//    @ResponseStatus(HttpStatus.NO_CONTENT)
//    @DeleteMapping("/api/delete/{id}")
//    @ResponseBody
//    void deleteById(@PathVariable Integer id) {
//        roomsRepository.deleteById(id);
//    }
}
