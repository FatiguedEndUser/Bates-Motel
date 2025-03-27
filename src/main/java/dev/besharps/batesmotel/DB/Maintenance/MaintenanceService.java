package dev.besharps.batesmotel.DB.Maintenance;

import dev.besharps.batesmotel.DB.Staff.Staff;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class MaintenanceService {
    @Autowired
    private MaintenanceRepository maintenanceRepository;

    public List<Maintenance> getAllMaintenance() {
        return maintenanceRepository.findAll();
    }

    public Optional<Maintenance> getMaintenanceById(Integer id) {
        return maintenanceRepository.findById(id);
    }

    public List<Maintenance> getMaintenanceByRoomId(Integer roomId) {
        return maintenanceRepository.findByRoomId(roomId);
    }

    public List<Maintenance> getMaintenanceByStaffId(Integer staffId) {
        return maintenanceRepository.findByStaffId(staffId);
    }

    public List<Maintenance> getPendingMaintenance() {
        return maintenanceRepository.findPendingMaintenance();
    }

    public Maintenance createMaintenance(Maintenance maintenance) {
        return maintenanceRepository.save(maintenance);
    }

    @Transactional
    public void updateMaintenance(Integer id, String description, LocalDate finishedDate, Integer staffId) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));

        if (description != null) {
            maintenance.setDescription(description);
        }

        if (finishedDate != null) {
            maintenance.setFinishedDate(finishedDate);
        }

        if (staffId != null) {
            // Note: In a real implementation, you would need to fetch the Staff entity
            // This is a simplified version
            Staff staff = maintenance.getStaff();
            staff.setStaffId(staffId);
            maintenance.setStaff(staff);
        }

        maintenanceRepository.save(maintenance);
    }

    @Transactional
    public void completeMaintenance(Integer id, LocalDate finishedDate) {
        Maintenance maintenance = maintenanceRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Maintenance not found"));

        maintenance.setFinishedDate(finishedDate != null ? finishedDate : LocalDate.now());
        maintenanceRepository.save(maintenance);
    }

    public void deleteMaintenance(Integer id) {
        maintenanceRepository.deleteById(id);
    }
}
