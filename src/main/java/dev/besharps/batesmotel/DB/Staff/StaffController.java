package dev.besharps.batesmotel.DB.Staff;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/staff")
public class StaffController {

    private final StaffRepository staffRepository;

    public StaffController(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    @GetMapping("/all")
    @ResponseStatus(HttpStatus.OK)
    public List<Staff> findAll() {
        return staffRepository.findAll();
    }

    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Optional<Staff> findById(@PathVariable String id) {
        return staffRepository.findById(id);
    }

    @PostMapping("/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Staff createStaff(@RequestBody Staff staff) {
        // Don't set ID for new staff - let the generator handle it
        return staffRepository.save(staff);
    }

    @PutMapping("/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    public Staff updateStaff(@PathVariable String id, @RequestBody Staff staff) {
        if (staffRepository.existsById(id)) {
            staff.setId(id); // Ensure ID is set correctly
            return staffRepository.save(staff);
        }
        return null; // In a real application, you might want to throw an exception
    }

    @DeleteMapping("/delete/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStaff(@PathVariable String id) {
        staffRepository.deleteById(id);
    }
}