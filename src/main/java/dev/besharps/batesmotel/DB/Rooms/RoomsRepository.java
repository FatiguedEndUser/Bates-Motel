package dev.besharps.batesmotel.DB.Rooms;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
    List<Rooms> findByRoomType(String roomType);
    List<Rooms> findByRoomNumber(Long roomNumber);
    List<Rooms> findByAvailableTrue();
    List<Rooms> findByAvailableFalse();
    List<Rooms> findByFloor(int floor);
    List<Rooms> findByRoomName(String roomName);

    // Added new search methods
    List<Rooms> findByRoomTypeAndAvailableTrue(String roomType);
    List<Rooms> findByPricePerNightLessThanEqual(Double maxPrice);
    List<Rooms> findByPricePerNightBetween(Double minPrice, Double maxPrice);
    List<Rooms> findByBedConfigurationContaining(String bedType);

    @Query("SELECT r FROM Rooms r WHERE r.roomType = :roomType AND r.pricePerNight <= :maxPrice AND r.available = true")
    List<Rooms> findAvailableRoomsByTypeAndMaxPrice(
            @Param("roomType") String roomType,
            @Param("maxPrice") Double maxPrice
    );

    @Transactional
    @Modifying
    @Query("UPDATE Rooms r SET r.roomType = :roomType WHERE r.roomId = :id")
    void updateRoomType(
            @Param("roomType") String roomType,
            @Param("id") Long id
    );

    @Transactional
    @Modifying
    @Query("UPDATE Rooms r SET r.available = :available WHERE r.roomId = :id")
    void updateAvailability(
            @Param("available") boolean available,
            @Param("id") Long id
    );
}