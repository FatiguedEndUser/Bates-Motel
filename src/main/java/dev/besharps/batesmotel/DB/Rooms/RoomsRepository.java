package dev.besharps.batesmotel.DB.Rooms;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoomsRepository extends JpaRepository<Rooms, Integer> {
    //Crud operations come from JpaRepository class,
    // but we can specify specific crud operations and have our predefined
    //FOR EXAMPLE:

    //JPQL - this is based on the class we created not the database.
    @Query("SELECT roomId FROM Rooms")
    List<Rooms> findByRoomId(Integer roomId);

    @Query("SELECT roomType FROM Rooms")
    List<Rooms> findByRoomType(int roomType);
}
