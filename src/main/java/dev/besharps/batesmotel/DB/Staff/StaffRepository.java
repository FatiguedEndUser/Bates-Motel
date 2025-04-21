package dev.besharps.batesmotel.DB.Staff;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StaffRepository extends JpaRepository<Staff, String> {
    // Basic CRUD operations are inherited from JpaRepository
    // You can add custom query methods here if needed
}