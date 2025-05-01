package dev.besharps.batesmotel.DB.Staff;

import org.springframework.stereotype.Service;

import java.sql.Date;
import java.util.List;
import java.util.Optional;

@Service
public class StaffService {
    private final StaffRepository staffRepository;

    public StaffService(StaffRepository staffRepository) {
        this.staffRepository = staffRepository;
    }

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(String id) {
        return staffRepository.findById(id);
    }

    public Staff saveStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public void deleteStaff(String id) {
        staffRepository.deleteById(id);
    }

    public Staff updateStaff(
            String id,
            String firstName,
            String lastName,
            String jobTitle,
            Date startDate,
            Date endDate,
            String imageUrl,
            String description,
            String contactEmail,
            String contactPhone,
            String department) {

        Staff staffToUpdate = staffRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Staff not found with id: " + id));

        staffToUpdate.setFirstName(firstName);
        staffToUpdate.setLastName(lastName);
        staffToUpdate.setJobTitle(jobTitle);
        staffToUpdate.setStartDate(startDate);
        staffToUpdate.setEndDate(endDate);
        staffToUpdate.setImageUrl(imageUrl);
        staffToUpdate.setDescription(description);
        staffToUpdate.setContactEmail(contactEmail);
        staffToUpdate.setContactPhone(contactPhone);
        staffToUpdate.setDepartment(department);

        return staffRepository.save(staffToUpdate);
    }

    public List<Staff> getStaffByDepartment(String department) {
        return staffRepository.findByDepartment(department);
    }
}