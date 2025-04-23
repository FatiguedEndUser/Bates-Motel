package dev.besharps.batesmotel.DB.Staff;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff() {
        return staffRepository.findAll();
    }

    public Optional<Staff> getStaffById(String id) {
        return staffRepository.findById(id);
    }

    public Staff createStaff(Staff staff) {
        return staffRepository.save(staff);
    }

    public Staff updateStaff(String id, Staff staff) {
        if (staffRepository.existsById(id)) {
            staff.setId(id);
            return staffRepository.save(staff);
        }
        return null;
    }

    public void deleteStaff(String id) {
        staffRepository.deleteById(id);
    }
}