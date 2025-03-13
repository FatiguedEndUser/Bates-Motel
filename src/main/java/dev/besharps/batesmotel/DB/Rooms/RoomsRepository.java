package dev.besharps.batesmotel.DB.Rooms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
    List<Rooms> findByRoomType(String roomType);
    List<Rooms> findByRoomNumber(Long roomNumber);
    List<Rooms> findByAvailableTrue();
    List<Rooms> findByAvailableFalse();
    List<Rooms> findByAvailableTrueAndRoomNumber(Long roomNumber);
}
