package dev.besharps.batesmotel.DB.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    // Basic CRUD operations are inherited from JpaRepository

    // Custom query methods for searching staff
    List<Staff> findByJobTitle(String jobTitle);
    List<Staff> findByFirstNameContainingIgnoreCase(String firstName);
    List<Staff> findByLastNameContainingIgnoreCase(String lastName);
    List<Staff> findByDepartment(String department);
}