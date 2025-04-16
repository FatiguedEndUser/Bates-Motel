package dev.besharps.batesmotel.DB.Maintenance;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import jakarta.transaction.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MaintenanceRepository extends JpaRepository<Maintenance, Integer> {
    @Query("SELECT m FROM Maintenance m WHERE m.room.roomId = :roomId")
    List<Maintenance> findByRoomId(@Param("roomId") Integer roomId);

    @Query("SELECT m FROM Maintenance m WHERE m.staff.staffId = :staffId")
    List<Maintenance> findByStaffId(@Param("staffId") Integer staffId);

    @Query("SELECT m FROM Maintenance m WHERE m.finishedDate IS NULL")
    List<Maintenance> findPendingMaintenance();

    @Query("SELECT m FROM Maintenance m WHERE m.requestDate BETWEEN :startDate AND :endDate")
    List<Maintenance> findByRequestDateBetween(
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate);

    @Transactional
    @Modifying
    @Query("UPDATE Maintenance m SET m.description = :description WHERE m.maintenanceId = :id")
    void updateDescription(
            @Param("description") String description,
            @Param("id") Integer id
    );

    @Transactional
    @Modifying
    @Query("UPDATE Maintenance m SET m.finishedDate = :finishedDate WHERE m.maintenanceId = :id")
    void updateFinishedDate(
            @Param("finishedDate") LocalDate finishedDate,
            @Param("id") Integer id
    );

    @Transactional
    @Modifying
    @Query("UPDATE Maintenance m SET m.staff.staffId = :staffId WHERE m.maintenanceId = :id")
    void updateStaffAssignment(
            @Param("staffId") Integer staffId,
            @Param("id") Integer id
    );
}
