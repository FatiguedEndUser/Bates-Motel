package dev.besharps.batesmotel.DB.Maintenance;

//DEPENDENCY IMPORTS
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//STANDARD LIB
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import dev.besharps.batesmotel.Exceptions.MaintenanceNotFoundException;

@RestController
@RequestMapping("/api/maintenance") // Changed to avoid conflict with MaintenanceMapping
public class MaintenanceController {
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    @Autowired
    private MaintenanceService maintenanceService;

    //GET
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find-all")  // Fixed duplicate path
    List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")  // Fixed duplicate path
    Optional<Maintenance> findById(@PathVariable Integer id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenance;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/room/{roomId}")
    List<Maintenance> findByRoomId(@PathVariable Integer roomId) {
        List<Maintenance> maintenanceList = maintenanceRepository.findByRoomId(roomId);
        if (maintenanceList.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenanceList;
    }


    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/pending")
    List<Maintenance> findPendingMaintenance() {
        List<Maintenance> maintenanceList = maintenanceRepository.findPendingMaintenance();
        if (maintenanceList.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenanceList;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/date-range")
    List<Maintenance> findByDateRange(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate) {
        List<Maintenance> maintenanceList = maintenanceRepository.findByRequestDateBetween(startDate, endDate);
        if (maintenanceList.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenanceList;
    }

    //POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/create")  // Fixed path
    void create(@Valid @RequestBody Maintenance maintenance) {
        maintenanceRepository.save(maintenance);
    }

    //PUT
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/update/{id}")
    void updateMaintenance(
            @PathVariable Integer id,
            @RequestParam(required = false) String description,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishedDate,
            @RequestParam(required = false) Integer staffId) {

        maintenanceService.updateMaintenance(id, description, finishedDate, staffId);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/complete/{id}")
    void completeMaintenance(
            @PathVariable Integer id,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishedDate) {

        maintenanceService.completeMaintenance(id, finishedDate);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/description/{id}")
    void updateDescription(@PathVariable Integer id, @RequestParam String description) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(MaintenanceNotFoundException::new);
        maintenance.setDescription(description);
        maintenanceRepository.save(maintenance);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("/update/finished-date/{id}")
    void updateFinishedDate(
            @PathVariable Integer id,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate finishedDate) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(MaintenanceNotFoundException::new);
        maintenance.setFinishedDate(finishedDate);
        maintenanceRepository.save(maintenance);
    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/delete/{id}")  // Fixed path
    void deleteById(@PathVariable Integer id) {
        maintenanceRepository.deleteById(id);
    }
}