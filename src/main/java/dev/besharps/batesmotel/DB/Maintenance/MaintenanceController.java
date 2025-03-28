package dev.besharps.batesmotel.DB.Maintenance;

//DEPENDENCY IMPORTS
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

//STANDARD LIB
import java.util.List;
import java.util.Optional;
import dev.besharps.batesmotel.Exceptions.MaintenanceNotFoundException;

@RestController
@RequestMapping("/maintenance")
public class MaintenanceController {
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    //GET
    @ResponseStatus(HttpStatus.FOUND)
    @GetMapping("/maintenance/find-all")
    List<Maintenance> findAll() {
        return maintenanceRepository.findAll();
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/maintenance/{id}")
    Optional<Maintenance> findById(@PathVariable Integer id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenance;
    }

    Optional<Maintenance> findByRoomId(@PathVariable Integer id) {
        Optional<Maintenance> maintenance = maintenanceRepository.findById(id);
        if (maintenance.isEmpty()) {
            throw new MaintenanceNotFoundException();
        }
        return maintenance;
    }

    //POST
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping("/maintenance/")
    void create(@Valid @RequestBody Maintenance maintenance) {
        maintenanceRepository.save(maintenance);
    }

    //PUT
    //What fields should be updatable?
    // -
    // -
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @PutMapping("")
    void updateMaintenance(@Valid @RequestBody Maintenance maintenance, @PathVariable Integer id) {
        //TODO implement update method to update a booking
    }

    //DELETE
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/maintenance/delete/{id}")
    void deleteById(@PathVariable Integer id) {
        maintenanceRepository.deleteById(id);
    }
}
