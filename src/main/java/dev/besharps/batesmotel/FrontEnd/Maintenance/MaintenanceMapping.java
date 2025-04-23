package dev.besharps.batesmotel.FrontEnd.Maintenance;

import dev.besharps.batesmotel.DB.Maintenance.Maintenance;
import dev.besharps.batesmotel.DB.Maintenance.MaintenanceRepository;
import dev.besharps.batesmotel.DB.Maintenance.MaintenanceService;
import dev.besharps.batesmotel.DB.Rooms.Rooms;
import dev.besharps.batesmotel.DB.Staff.Staff;
import dev.besharps.batesmotel.Exceptions.MaintenanceNotFoundException;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/maintenance")
public class MaintenanceMapping {
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MaintenanceService maintenanceService;

    // Frontend view mappings
    @GetMapping
    public String viewAllMaintenance(Model model) {
        List<Maintenance> allMaintenance = maintenanceService.getAllMaintenance();
        model.addAttribute("isAdmin", hasAdminRole());
        model.addAttribute("maintenanceList", allMaintenance);
        model.addAttribute("searching", false);
        return "Maintenance";
    }

    @GetMapping("/pending")
    public String viewPendingMaintenance(Model model) {
        List<Maintenance> pendingMaintenance = maintenanceService.getPendingMaintenance();
        model.addAttribute("isAdmin", hasAdminRole());
        model.addAttribute("maintenanceList", pendingMaintenance);
        model.addAttribute("maintenanceStatus", "Pending");
        model.addAttribute("searching", false);
        return "Maintenance";
    }

    @GetMapping("/completed")
    public String viewCompletedMaintenance(Model model) {
        List<Maintenance> completedMaintenance = maintenanceRepository.findByFinishedDateNotNull();
        model.addAttribute("isAdmin", hasAdminRole());
        model.addAttribute("maintenanceList", completedMaintenance);
        model.addAttribute("maintenanceStatus", "Completed");
        model.addAttribute("searching", false);
        return "Maintenance";
    }

    @GetMapping("/room/{roomId}")
    public String viewMaintenanceByRoom(@PathVariable Integer roomId, Model model) {
        List<Maintenance> roomMaintenance = maintenanceService.getMaintenanceByRoomId(roomId);
        model.addAttribute("isAdmin", hasAdminRole());
        model.addAttribute("maintenanceList", roomMaintenance);
        model.addAttribute("roomId", roomId);
        model.addAttribute("searching", false);
        return "Maintenance";
    }

    @GetMapping("/search")
    public String searchMaintenance(
            @RequestParam(required = false) Integer roomId,
            @RequestParam(required = false) Boolean pending,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            @RequestParam(required = false) String keyword,
            Model model) {

        List<Maintenance> maintenanceResults;

        // Handle date range search - ensure BOTH dates are provided
        if (startDate != null && endDate != null) {
            // Make sure endDate is not before startDate
            if (endDate.isBefore(startDate)) {
                // Swap dates if they're in the wrong order
                LocalDate temp = startDate;
                startDate = endDate;
                endDate = temp;
            }

            // Get maintenance requests within the specified date range
            maintenanceResults = maintenanceRepository.findByRequestDateBetween(startDate, endDate);
            System.out.println("Date range search from " + startDate + " to " + endDate + ": found " + maintenanceResults.size() + " results");
        }
        // Handle other search criteria
        else if (pending != null) {
            // Code for pending status remains the same
            if (pending) {
                maintenanceResults = maintenanceRepository.findPendingMaintenance();
            } else {
                maintenanceResults = maintenanceRepository.findByFinishedDateNotNull();
            }
        }
        // Rest of the code remains the same
        else if (roomId != null) {
            maintenanceResults = maintenanceRepository.findByRoomId(roomId);
        }
        else if (keyword != null && !keyword.isEmpty()) {
            maintenanceResults = maintenanceRepository.findByDescriptionContaining(keyword);
        }
        else {
            maintenanceResults = maintenanceService.getAllMaintenance();
        }

        // Add attributes to model
        model.addAttribute("isAdmin", hasAdminRole());
        model.addAttribute("maintenanceList", maintenanceResults);
        model.addAttribute("roomId", roomId);
        model.addAttribute("pending", pending);
        model.addAttribute("startDate", startDate);
        model.addAttribute("endDate", endDate);
        model.addAttribute("keyword", keyword);
        model.addAttribute("searching", true);

        return "MaintenanceSearch";
    }

    // Admin page for managing maintenance
    @GetMapping("/admin/manage")
    public String manageMaintenance(Model model) {
        List<Maintenance> allMaintenance = maintenanceService.getAllMaintenance();
        model.addAttribute("isAdmin", hasAdminRole());
        model.addAttribute("maintenanceList", allMaintenance);
        model.addAttribute("newMaintenance", new Maintenance());
        return "MaintenanceManagement";
    }

    @PostMapping("/admin/add")
    public String addMaintenance(@ModelAttribute("newMaintenance") Maintenance maintenance) {
        // Set request date to today if not provided
        if (maintenance.getRequestDate() == null) {
            maintenance.setRequestDate(LocalDate.now());
        }

        maintenanceService.createMaintenance(maintenance);
        return "redirect:/maintenance/admin/manage";
    }

    @GetMapping("/admin/edit/{id}")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        model.addAttribute("isAdmin", hasAdminRole());
        model.addAttribute("maintenance", maintenance.get());
        return "EditMaintenance";
    }

    @PostMapping("/admin/update/{id}")
    public String updateMaintenance(
            @PathVariable Integer id,
            @ModelAttribute Maintenance maintenance) {

        maintenanceService.updateMaintenance(
                id,
                maintenance.getDescription(),
                maintenance.getFinishedDate(),
                (maintenance.getStaff() != null) ? Integer.valueOf(maintenance.getStaff().getId()) : null
        );

        return "redirect:/maintenance/admin/manage";
    }

    @GetMapping("/admin/complete/{id}")
    public String completeMaintenance(@PathVariable Integer id) {
        maintenanceService.completeMaintenance(id, LocalDate.now());
        return "redirect:/maintenance/admin/manage";
    }

    @GetMapping("/admin/delete/{id}")
    public String deleteMaintenance(@PathVariable Integer id) {
        maintenanceService.deleteMaintenance(id);
        return "redirect:/maintenance/admin/manage";
    }

    // REST API endpoints
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/api/all")
    @ResponseBody
    List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/{id}")
    @ResponseBody
    Optional<Maintenance> findById(@PathVariable Integer id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenance;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/api/room/{roomId}")
    @ResponseBody
    List<Maintenance> findByRoomId(@PathVariable Integer roomId) {
        List<Maintenance> maintenanceList = maintenanceRepository.findByRoomId(roomId);
        if (maintenanceList.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }

        return maintenanceList;
    }

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/api/create")
    @ResponseBody
    void create(@Valid @RequestBody Maintenance maintenance) {
        maintenanceRepository.save(maintenance);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/api/update")
    @ResponseBody
    public Maintenance update(@Valid @RequestBody Maintenance maintenance) {
        if (!maintenanceRepository.existsById(maintenance.getMaintenanceId())) {
            throw new MaintenanceNotFoundException();
        }
        return maintenanceRepository.save(maintenance);
    }
    private boolean hasAdminRole() {

        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth != null && auth.getAuthorities().stream()
                    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));
        } catch (Exception e) {

            return false;
        }
    }
}