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

    //Backend Funtions
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
}
