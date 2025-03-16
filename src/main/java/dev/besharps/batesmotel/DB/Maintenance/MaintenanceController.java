package dev.besharps.batesmotel.DB.Maintenance;

import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import dev.besharps.batesmotel.Exceptions.MaintenanceNotFoundException;

@RestController
@RequestMapping("/maintenance")
@NoArgsConstructor
public class MaintenanceController {
    private MaintenanceRepository maintenanceRepository;
    public MaintenanceController(MaintenanceRepository maintenanceRepository) {
        this.maintenanceRepository = maintenanceRepository;
    }

    //GET
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/find-all")
    List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/{id}")
    Optional<Maintenance> findMaintenanceById(@PathVariable Integer id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenanceRepository.findById(id);
    }

    Optional<Maintenance> findByRoomId(@PathVariable Integer id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenance;
    }

    //POST

    //PUT

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    void deleteById(@PathVariable Integer id) {
        maintenanceRepository.deleteById(id);
    }
}
